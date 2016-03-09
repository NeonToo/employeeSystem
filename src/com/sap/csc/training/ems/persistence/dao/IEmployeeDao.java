package com.sap.csc.training.ems.persistence.dao;

import java.util.List;

import com.sap.csc.training.ems.persistence.model.Employee;

public interface IEmployeeDao {
	public String add(Employee employee);

	public String update(Employee employee);
	
	public String delete(long id);

	public List<Employee> findList();

	public Employee find(long id);
}
