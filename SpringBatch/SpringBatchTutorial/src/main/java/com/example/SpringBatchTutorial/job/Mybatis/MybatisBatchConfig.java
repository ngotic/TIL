package com.example.SpringBatchTutorial.job.Mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.SpringBatchTutorial.core.domain.Person;
import com.example.SpringBatchTutorial.job.JobListenter.JobLoggerListener;

import lombok.extern.slf4j.Slf4j;


// 스프링 배치는 같은 값으로 실행하면 이미 실행중인 인스턴스라고 뜨며 RunIdIncrementer를 넣어야 한다.
// MyBatisPagingItemReader 사용시에는 xml 쿼리에 페이징 
// LIMIT #{_pagesize}, offset #{_skiprows} 지정을 꼭 써야한다. > 안쓰면 무한루프 

@Slf4j
@Configuration
public class MybatisBatchConfig {
	
		@Autowired 
		public SqlSessionFactory sqlSessionFactory;
		
		@Bean
		public Job jobMybatisJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception { //Job실행시 ID 부여, 시퀀스를 순차적으로 부여할 수 있도록 이렇게 한다.
			return new JobBuilder("MyBatisJob", jobRepository)   
					.listener(new JobLoggerListener())
					.incrementer(new RunIdIncrementer()) // 이걸 써야 재실행이 가능하다.
					.start(jobMybatisStep(jobRepository, transactionManager)) // Job안에는 step이 존재한다.
					
					.build();
		}
	
		@Bean
		public Step jobMybatisStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception  {
			return new StepBuilder("step", jobRepository)
					.<Person, Person>chunk(5, transactionManager) // chunk 단위로 아이템을 처리한다. 
					.reader(reader())
					.processor(processor())
					.writer(writer())
					.build();
		} 
	
		// Reader쪽 정의 
	    @Bean
	    @StepScope
	    public MyBatisPagingItemReader<Person> reader() throws Exception {
	    	// 파라미터 넣기
	        Map<String,Object> parameterValues = new HashMap<>();
	        parameterValues.put("personid", 10);
	    	
	        return new MyBatisPagingItemReaderBuilder<Person>()
	        		.pageSize(5)
	                .sqlSessionFactory(sqlSessionFactory)
	                .queryId("com.example.SpringBatchTutorial.mapper.PersonMapper.getPerson") //여기에 sql문을 따로 정의해줘야 한다.
	                .parameterValues(parameterValues)
	                .build();
	    }

	    @Bean
	    @StepScope
	    public ItemProcessor<Person, Person> processor(){

	        return new ItemProcessor<Person, Person>() {
	            @Override
	            public Person process(Person person) throws Exception {
	                // 프로세싱 '로직' 
	                System.out.println("person 출력 : "+person);
	                return person;
	            }
	        };
	    }
	    
	    // Writer쪽 정의> 후처리나 다시쓰는 로직인데
	    @Bean
	    @StepScope
	    public MyBatisBatchItemWriter<Person> writer(){
	        return new MyBatisBatchItemWriterBuilder<Person>()
	                .assertUpdates(false)
	                .sqlSessionFactory(sqlSessionFactory)
	                .statementId("com.example.SpringBatchTutorial.mapper.PersonMapper.insertPerson")
	                .build();
	    }
}
