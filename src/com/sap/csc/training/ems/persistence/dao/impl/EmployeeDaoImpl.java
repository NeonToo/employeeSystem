package com.sap.csc.training.ems.persistence.dao.impl;

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
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import com.sap.csc.training.ems.document.DocumentService;
import com.sap.csc.training.ems.persistence.dao.IEmployeeDao;
import com.sap.csc.training.ems.persistence.model.Employee;

public class EmployeeDaoImpl implements IEmployeeDao {
	private EntityManagerFactory emf;
	private DataSource ds;
	private EntityManager em;

	public EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
		Connection connection = null;

		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
			connection = ds.getConnection();
			Map properties = new HashMap();
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			emf = Persistence.createEntityManagerFactory("employeeSystem",
					properties);
			em = emf.createEntityManager();
		} catch (NamingException | SQLException e) {

		}
	}

	@Override
	public String add(Employee employee) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();

		return "ok";
	}

	@Override
	public String update(Employee employee) {
		// TODO Auto-generated method stub
		return "ok";
	}

	@Override
	public List<Employee> findList() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAll").getResultList();
	}

	@Override
	public Employee find(long id) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		Employee employee = em.find(Employee.class, id);
		em.getTransaction().commit();

		return employee;
	}

	@Override
	public String delete(long id) {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		Employee employee = em.find(Employee.class, id);
		em.remove(employee);
		em.getTransaction().commit();
		
		try {
			DocumentService documentService = new DocumentService();
			documentService.deleteImg(employee.getImgId());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "ok";
	}

}
