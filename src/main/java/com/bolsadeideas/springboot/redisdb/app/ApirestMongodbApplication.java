package com.bolsadeideas.springboot.redisdb.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.bolsadeideas.springboot.redisdb.app.model.Employee;
import com.bolsadeideas.springboot.redisdb.app.repository.EmployeeRepository;

@SpringBootApplication
public class ApirestMongodbApplication implements CommandLineRunner {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ApirestMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection(Employee.class);
		
		List<Employee> lista = new ArrayList<>();
		lista.add(new Employee(String.valueOf(new Random().nextInt()), "Christopher", "Sanz", "christo3@gmail.com"));
		lista.add(new Employee(String.valueOf(new Random().nextInt()), "Gaela", "Herrera", "gaelita@gmail.com"));
		
		lista.stream()
		.map(emp -> {
			emp.setFirstName(emp.getFirstName().toUpperCase());
			emp.setLasteName(emp.getLasteName().toUpperCase());
			System.out.print("Insert: " + emp.getFirstName() + " " + emp.getLasteName() + " " + emp.getEmail());
			return employeeRepository.save(emp);
		})
		.collect(Collectors.toList());
		
	}

}
