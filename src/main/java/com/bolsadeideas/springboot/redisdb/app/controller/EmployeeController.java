package com.bolsadeideas.springboot.redisdb.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.redisdb.app.model.Employee;
import com.bolsadeideas.springboot.redisdb.app.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	@Cacheable("employeesListCache")
	public List<Employee> getEmployees(){
		simulateSlowService();
		List<Employee> employeesList = (List<Employee>) employeeRepository.findAll();
		return employeesList;
	}
	
	@GetMapping("/employees/{id}")
	public Optional<Employee> getEmployee(@PathVariable String id){
		Optional<Employee> employee = employeeRepository.findById(id);
		return employee;
	}
	
	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee newEmployee){
		String id = String.valueOf(new Random().nextInt());
		Employee emp = new Employee(id, newEmployee.getFirstName(), newEmployee.getLasteName(), newEmployee.getEmail() );
		employeeRepository.save(emp);
		return emp;
	}
	
	@PutMapping("/employee/{id}")
	public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable String id){
		Optional<Employee> optionalEmp = employeeRepository.findById(id);
		if(optionalEmp.isPresent()) {
			Employee emp = optionalEmp.get();
			emp.setFirstName(newEmployee.getFirstName());
			emp.setLasteName(newEmployee.getLasteName());
			emp.setEmail(newEmployee.getEmail());
			employeeRepository.save(emp);
		}
		return optionalEmp;
	}
	
	@DeleteMapping("/employee/{id}")
	@CacheEvict("employeesListCache")
	public String deleteEmployee(@PathVariable String id){
		Boolean result = employeeRepository.existsById(id);
		employeeRepository.deleteById(id);
		return "{ \"operacionExitosa\" : "+ (result ? "true" : "false") + " }";
	}
	
	private void simulateSlowService() {
    	try {
    		log.info("Dormir por un tiempo de espera de Secs");
	        Thread.sleep(1000*5);
	    } catch (InterruptedException e) {
	      throw new IllegalStateException(e);
	    }
	}
	

}
