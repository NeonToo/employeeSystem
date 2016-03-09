package com.sap.csc.training.ems.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sap.csc.training.ems.document.DocumentService;
import com.sap.csc.training.ems.persistence.model.Employee;
import com.sap.csc.training.ems.persistence.service.IEmployeeService;
import com.sap.csc.training.ems.persistence.service.impl.EmployeeServiceImpl;

/**
 * Servlet implementation class EmployeeController
 */
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IEmployeeService employeeService = new EmployeeServiceImpl();
	private String method;
	private String result;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeController()  {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		PrintWriter out= response.getWriter();
		method = request.getParameter("method");
		Employee employee;
		List<Employee> employees = null;
		
		switch (method) {
		case "add":
			result = employeeService.add(request, response);
			break;
		case "find":
			employee = employeeService.find(request, response);
			JSONObject jo = JSONObject.fromObject(employee);
			response.setCharacterEncoding("utf-8");
			response.getOutputStream().write(jo.toString().getBytes("UTF-8"));
			response.setContentType("text/json;charset=utf-8");
			break;
		case "getAll":
			employees = employeeService.getAll(request, response);
			JSONArray ja = JSONArray.fromObject(employees);
			if (employees.isEmpty()) {
				System.out.println("Database is empty");
				DocumentService documentService;
				try {
					documentService = new DocumentService();
					documentService.deleteAll();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				response.setCharacterEncoding("utf-8");
				response.getOutputStream().write(ja.toString().getBytes("UTF-8"));
				response.setContentType("text/json;charset=utf-8");
			}
			break;
		case "edit":
			result = employeeService.update(request, response);
			break;
		case "delete":
			result = employeeService.delete(request, response);
			break;
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
