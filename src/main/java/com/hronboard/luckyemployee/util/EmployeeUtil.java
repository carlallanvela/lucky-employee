package com.hronboard.luckyemployee.util;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.hronboard.luckyemployee.employee.Employee;

@Component
public class EmployeeUtil {
	
	public Employee getLuckyEmployee(List<Employee> allEmployees) {
		SortedSet<Date> dates = new TreeSet<Date>();
		
		for (Employee employee : allEmployees) {
			dates.add(employee.getLastErrandDate());
		}
		
		Date furthestDate = dates.first();
		
		for (Employee employee : allEmployees) {
			if (employee.isPresent() && employee.getLastErrandDate().equals(furthestDate)) {
				return employee;
			}
		}
		
		return null;
	}
}
