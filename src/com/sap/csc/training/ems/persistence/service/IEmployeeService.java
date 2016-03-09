package com.sap.csc.training.ems.persistence.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.csc.training.ems.persistence.model.Employee;

public interface IEmployeeService {
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException;

	public String update(HttpServletRequest request,
			HttpServletResponse response);

	public String delete(HttpServletRequest request,
			HttpServletResponse response);

	public Employee find(HttpServletRequest request,
			HttpServletResponse response);

	public List<Employee> getAll(HttpServletRequest request,
			HttpServletResponse response);
}
