package com.sap.csc.training.ems.persistence.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sap.csc.training.ems.persistence.dao.IEmployeeDao;
import com.sap.csc.training.ems.persistence.dao.impl.EmployeeDaoImpl;
import com.sap.csc.training.ems.persistence.model.Employee;
import com.sap.csc.training.ems.persistence.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {
	IEmployeeDao employeeDao = new EmployeeDaoImpl();
	String result;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		String oInfo = request.getParameter("oInfo");
		JSONObject jo = JSONObject.fromObject(oInfo);
		String firstName = jo.getString("firstName");
		String lastName = jo.getString("lastName");
		String imgId=jo.getString("imgId");
		
		if (firstName != null && lastName != null) {
			Employee employee = new Employee();

			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setImgId(imgId);
			
			result = employeeDao.add(employee);
		}
		return result;
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		long id = Long.parseLong(request.getParameter("id"));
		String oInfo = request.getParameter("oInfo");
		JSONObject jo = JSONObject.fromObject(oInfo);
		String firstName = jo.getString("firstName");
		String lastName = jo.getString("lastName");
		
		if (firstName != null && lastName != null) {
			Employee employee = employeeDao.find(id);

			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			
			result = employeeDao.update(employee);
		}
		
		return result;
	}

	@Override
	public String delete(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		long id = Long.parseLong(request.getParameter("id"));
		
		return employeeDao.delete(id);
	}

	@Override
	public Employee find(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		long id = Long.parseLong(request.getParameter("id"));
		
		return employeeDao.find(id);
	}

	public List<Employee> getAll(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return employeeDao.findList();
	}
}
