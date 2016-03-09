package com.sap.csc.training.ems.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "T_EMPLOYEE")
@NamedQuery(name = "getAll", query = "select p from Employee p")
public class Employee {
	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;
	@Basic
	private String firstName;
	@Basic
	private String lastName;
	@Basic
	private String imgId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
}
