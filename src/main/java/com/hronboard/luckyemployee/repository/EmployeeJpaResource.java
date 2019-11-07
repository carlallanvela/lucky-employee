package com.hronboard.luckyemployee.repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hronboard.luckyemployee.employee.Employee;
import com.hronboard.luckyemployee.exception.EmployeeNotFoundException;
import com.hronboard.luckyemployee.util.EmployeeUtil;

@CrossOrigin(origins = "http://lucky-employee.s3-website-ap-southeast-2.amazonaws.com")
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeJpaResource {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeUtil employeeUtil;
		
	@GetMapping(path="/jpa/employees")
	public List<Employee> retrieveAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@GetMapping(path="/jpa/employees/{id}")
	public Optional<Employee> retrieveEmployee(@PathVariable int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		
		if (!employee.isPresent()) {
			throw new EmployeeNotFoundException("id: " + id);
		}

		Resource<Employee> resource = new Resource<Employee>(employee.get());
		
		ControllerLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllEmployees());
		
		resource.add(linkTo.withRel("all-employees"));
		
		return employee;
	}
	
	@GetMapping(path="/jpa/employees/luckyemployee")
	public Employee retrieveLuckyEmployee() {
		List<Employee> allEmployees = employeeRepository.findAll();
		
		for (Employee employee : allEmployees) {
			employee.setLucky(false);
		}
		
		employeeRepository.saveAll(allEmployees);
		
		Employee luckyEmployee = employeeUtil.getLuckyEmployee(allEmployees);
		
		if (luckyEmployee != null) {
			luckyEmployee.setLucky(true);
			luckyEmployee.setLastErrandDate(new Date());
			createEmployee(luckyEmployee);
		}
		
		return luckyEmployee;
	}

	@PostMapping(path="/jpa/employees")
	public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedEmployee.getId())
			.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/employees/{id}")
	public void deleteAccount(@PathVariable int id) {
		employeeRepository.deleteById(id);
	}
}
