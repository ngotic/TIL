package com.example.SpringBatchTutorial.job.ValidatedParam.Validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileParamValidator implements JobParametersValidator {

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		String fileName = parameters.getString("fileName");
		log.info("fileName : > " + fileName);
		if(!StringUtils.endsWithIgnoreCase(fileName, ".csv")) {
			throw new JobParametersInvalidException("This is Not CSV Error");
		}
	}	

}
