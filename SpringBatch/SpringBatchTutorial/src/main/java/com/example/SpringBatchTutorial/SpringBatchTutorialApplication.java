package com.example.SpringBatchTutorial;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import java.util.UUID;



// --spring.batch.job.name=helloWorldJob version=2

 // 이걸 반드시 추가해야 한다.
// @EnableBatchProcessing //< 이거 쓰면 절대 안된다.  
@SpringBootApplication()
@ComponentScan(basePackages = {"com.example.SpringBatchTutorial"})
public class SpringBatchTutorialApplication {

	public static void main(String[] args) {
		System.out.println(args[0]);
		SpringApplication.run(SpringBatchTutorialApplication.class, args); 
	}
	
// enable=false로 하고 아래 runner를 이용해서 실행가능하다. > 일반적으로는 자동 배치 실행	
//	@Bean 
//	ApplicationRunner runner(JobLauncher jobLauncher, Job job) {
//		return args -> {
//				var jobParameters = new JobParametersBuilder()
//						.addString("uuid", UUID.randomUUID().toString())
//						.toJobParameters();
//				
//				var run = jobLauncher.run(job, jobParameters);
//				var instanceId = run.getJobInstance().getInstanceId();
//				// System.out.println("instanceId :" + instanceId);
//		};
//	}

}
