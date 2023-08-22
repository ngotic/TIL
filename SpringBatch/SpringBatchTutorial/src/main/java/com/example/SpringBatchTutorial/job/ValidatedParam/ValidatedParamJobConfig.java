package com.example.SpringBatchTutorial.job.ValidatedParam;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.SpringBatchTutorial.job.HelloWorld.HelloWorldJobConfig;
import com.example.SpringBatchTutorial.job.ValidatedParam.Validator.FileParamValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




// 짝대기(-) 빼야 한다. 
//--spring.batch.job.name=validatedParamJob fileName=test.csv


// 특정 타입이나 값을 파라미터로 넘겨주고 싶다. 어떻게 받음? 검증 어떻게 함? 
@RequiredArgsConstructor
@Slf4j
@Configuration
public class ValidatedParamJobConfig {
	
	
	@Bean // Step을 호출할 때는 주입 받아서 호출한다. 
	public Job validatedParamJob(JobRepository jobRepository, Step validatedParamStep) {  
		return new JobBuilder("validatedParamJob", jobRepository)
				// .validator(new FileParamValidator())
				.validator(multipleValidator())
				.start(validatedParamStep)  
				.build();
	}

    private CompositeJobParametersValidator multipleValidator() {
    	CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
    	validator.setValidators(Arrays.asList(new FileParamValidator())); // 이걸로 다수의 Validator를 등록할 수 있다. 
    	return validator;
    }
	
    @Bean
    @JobScope // 이거 써야 한다. 
    public Step validatedParamStep(
    		 @Value("#{jobParameters['fileName']}") String fileName,
             JobRepository jobRepository,
             PlatformTransactionManager transactionManager)  {
    	
		return new StepBuilder("step", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
					// log.info("fileName : "+fileName);
					/*JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
					String fileName2 = jobParameters.getString("fileName");
					log.info("validated Param tasklet! = "+fileName);
					*/
					log.info("validated fileName = "+fileName); // - 안붙여야 한다.... 
                    log.info("validated Param tasklet!");
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();
	}
	
	
	/*
	@Bean
	public Step validatedParamStep(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {
		return new StepBuilder("step", jobRepository)
				.tasklet(myTasklet2(), transactionManager)
				.build();
	}*/
	
	/*
	@Bean
	public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
		
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				log.info("fileName : "+fileName);
				log.info("validated Param Tasklet");
				return RepeatStatus.FINISHED;
			}
			
		};
		
		
	}*/
	
	
	
}
