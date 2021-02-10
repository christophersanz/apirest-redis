package com.bolsadeideas.springboot.redisdb.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bolsadeideas.springboot.redisdb.app.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
