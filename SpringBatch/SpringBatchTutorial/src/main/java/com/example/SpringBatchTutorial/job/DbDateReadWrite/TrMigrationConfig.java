package com.example.SpringBatchTutorial.job.DbDateReadWrite;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.SpringBatchTutorial.job.JobListenter.JobListenerConfig;
import com.example.SpringBatchTutorial.job.JobListenter.JobLoggerListener;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class TrMigrationConfig {
	
	@Autowired 
	public SqlSessionFactory sqlSessionFactory;
	
	@Bean
	public Job jobMrJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) { //Job실행시 ID 부여, 시퀀스를 순차적으로 부여할 수 있도록 이렇게 한다.

		return new JobBuilder("trMigrationJob", jobRepository)   
				.listener(new JobLoggerListener())
				.start(jobMrStep(jobRepository, transactionManager)) // Job안에는 step이 존재한다. 
				.build();
	}
	
	
	@Bean
	public Step jobMrStep(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {

		return new StepBuilder("step", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
					log.info("trMigrationJob step1!");
					return RepeatStatus.FINISHED;
                }, transactionManager).build();
	} 
}
