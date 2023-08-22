package com.example.SpringBatchTutorial.job.HelloWorld;


// 여기서 워닝이 떠서 명시적인 방법으로 수정하기 



import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



// --spring.batch.job.name=helloWorldJob version=2
@RequiredArgsConstructor
@Slf4j
@Configuration
public class HelloWorldJobConfig {
	
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job helloWorldJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) { //Job실행시 ID 부여, 시퀀스를 순차적으로 부여할 수 있도록 이렇게 한다.
//		return jobBuilderFactory.get("helloWorldJob")   
//				.incrementer(new RunIdIncrementer())
//				.start(helloWorldStep()) // Job안에는 step이 존재한다. 
//				.build();
		return new JobBuilder("helloWorldJob", jobRepository)   
				.start(helloWorldStep1(jobRepository, transactionManager)) // Job안에는 step이 존재한다. 
				.next(helloWorldStep2(jobRepository, transactionManager))
				.build();
	}
	
	
	// Step 하위에는 간단한 경우에는 tasklet으로 구동시킨다. 
	
	
	@Bean
	public Step helloWorldStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {
//		return stepBuilderFactory.get("helloWorldStep")
//				.tasklet(helloWorldTasklet())
//				.build();
		return new StepBuilder("step", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    log.info("Hello world step1!");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
	} 
	
	// 여기는 빈을 지정안한다. 
	public Step helloWorldStep2(JobRepository jobRepository , PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("hello world step2!");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
    }
	
// ItemProcessor,ItemReader, ItemWriter로 스탭을 구성할 수 있다.  
// 단순한거 처리는 tasklet쓴다. 
	
//	@StepScope
//	@Bean
//	public Tasklet helloWorldTasklet() {
//		return new Tasklet() {
//			@Override
//			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//				System.out.println("Hello World Spring Batch");
//				return RepeatStatus.FINISHED;
//			}
//		};
//	}

// 간단한 작업이라면 tasklet, itemReader, itemWriter는 복잡한거 
	
}
