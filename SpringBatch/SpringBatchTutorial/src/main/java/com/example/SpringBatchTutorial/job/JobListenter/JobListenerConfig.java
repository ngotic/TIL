package com.example.SpringBatchTutorial.job.JobListenter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



//--spring.batch.job.name=jobListenerJob


// Job 실행전 로그 쌓기 
@RequiredArgsConstructor
@Slf4j
@Configuration
public class JobListenerConfig {

	
	/*
	2023-08-21T16:37:52.050+09:00  INFO 17552 --- [           main] c.e.S.j.JobListenter.JobLoggerListener   : jobListenerJob Job is Running
	2023-08-21T16:37:52.057+09:00  INFO 17552 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step]
	2023-08-21T16:37:52.067+09:00  INFO 17552 --- [           main] c.e.S.j.JobListenter.JobListenerConfig   : jobListenerJob step1!
	2023-08-21T16:37:52.073+09:00  INFO 17552 --- [           main] o.s.batch.core.step.AbstractStep         : Step: [step] executed in 13ms
	2023-08-21T16:37:52.076+09:00  INFO 17552 --- [           main] c.e.S.j.JobListenter.JobLoggerListener   : jobListenerJob Job is Done. (Status: COMPLETED)
	*/
	
	@Bean
	public Job jobListenerJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) { //Job실행시 ID 부여, 시퀀스를 순차적으로 부여할 수 있도록 이렇게 한다.

		return new JobBuilder("jobListenerJob", jobRepository)   
				.listener(new JobLoggerListener())
				.start(jobListenerStep(jobRepository, transactionManager)) // Job안에는 step이 존재한다. 
				.build();
	}
	// Step 하위에는 간단한 경우에는 tasklet으로 구동시킨다. 
	
	
	@Bean
	public Step jobListenerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {

		return new StepBuilder("step", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
					
					// 성공케이스
					// log.info("jobListenerJob step1!");
					// return RepeatStatus.FINISHED;
                    
                    
                    // 실패케이스 
                    throw new Exception("Failed!!!");
                    
                }, transactionManager).build();
	} 

}
