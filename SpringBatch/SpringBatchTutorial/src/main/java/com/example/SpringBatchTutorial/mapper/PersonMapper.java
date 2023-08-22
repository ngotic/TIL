package com.example.SpringBatchTutorial.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.SpringBatchTutorial.core.domain.Person;

@Mapper
public interface PersonMapper {
	List<Person> getPerson();
	int insertPerson(Person person);
	int countPerson();
}
