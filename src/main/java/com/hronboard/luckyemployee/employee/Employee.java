package com.hronboard.luckyemployee.employee;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Employee Bean.
 * @author Carl Allan Vela
 *
 */
@ApiModel(description="All details about the Employee.")
@Entity
public class Employee {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message = "Employee Name should have at least 2 characters.")
	@ApiModelProperty(notes="Employee Name should have at least 2 characters. ")
	private String employeeName;

	private Date lastErrandDate;
	
	private boolean isPresent;
	
	private boolean isLucky;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getLastErrandDate() {
		return lastErrandDate;
	}

	public void setLastErrandDate(Date lastErrandDate) {
		this.lastErrandDate = lastErrandDate;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

	public boolean isLucky() {
		return isLucky;
	}

	public void setLucky(boolean isLucky) {
		this.isLucky = isLucky;
	}
	
	public Employee() {}
	
	public Employee(Integer id,
			@Size(min = 2, message = "Employee Name should have at least 2 characters.") String employeeName,
			@Past(message = "Last Errand Date should be in the past.") Date lastErrandDate, boolean isPresent,
			boolean isLucky) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.lastErrandDate = lastErrandDate;
		this.isPresent = isPresent;
		this.isLucky = isLucky;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", lastErrandDate=" + lastErrandDate
				+ ", isPresent=" + isPresent + ", isLucky=" + isLucky + "]";
	}
}
