package com.nqt.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "aht_employee")
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//@JsonIgnoreProperties(ignoreUnknown = false)
//@Proxy(lazy = false)
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Long empId;

	@Column(name = "emp_name")
	private String empName;
	
	@Column(name = "emp_dod")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date empDob;

	@Column(name = "emp_sex")
	private Integer empSex;

	@Column(name = "emp_number_insurance")
	private String empNumberInsurance;

	@Column(name = "emp_address")
	private String empAddress;

	@Column(name = "emp_phone")
	private String empPhone;

	@Column(name = "emp_type")
	private Integer empType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department departmentId;
	
	//@ElementCollection(targetClass = ContracEmp.class)
	@OneToMany( fetch = FetchType.EAGER, mappedBy = "empConId")
	//@LazyCollection(LazyCollectionOption.FALSE)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<ContracEmp> contracEmp = new HashSet<ContracEmp>(0);
	
	//@ElementCollection(targetClass = TrainningEmployee.class)
	//@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany( fetch = FetchType.EAGER, mappedBy = "emTrainId")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<TrainningEmployee> trainEmp = new HashSet<TrainningEmployee>(0);

	public Employee() {
	}

	public Employee(Long empId, String empName, Date empDob, Integer empSex, String empNumberInsurance,
			String empAddress, String empPhone, Integer empType, Department departmentId, Set<ContracEmp> contracEmp, Set<TrainningEmployee> trainEmp
			) {
		this.empId = empId;
		this.empName = empName;
		this.empDob = empDob;
		this.empSex = empSex;
		this.empNumberInsurance = empNumberInsurance;
		this.empAddress = empAddress;
		this.empPhone = empPhone;
		this.empType = empType;
		this.departmentId = departmentId;
		this.contracEmp = contracEmp;
		this.trainEmp = trainEmp;
		
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getEmpDob() {
		return empDob;
	}

	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}

	public Integer getEmpSex() {
		return empSex;
	}

	public void setEmpSex(Integer empSex) {
		this.empSex = empSex;
	}

	public String getEmpNumberInsurance() {
		return empNumberInsurance;
	}

	public void setEmpNumberInsurance(String empNumberInsurance) {
		this.empNumberInsurance = empNumberInsurance;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public Integer getEmpType() {
		return empType;
	}

	public void setEmpType(Integer empType) {
		this.empType = empType;
	}

	public Department getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}

	
	public Set<ContracEmp> getContracEmp() {
		return contracEmp;
	}

	public void setContracEmp(Set<ContracEmp> contracEmp) {
		this.contracEmp = contracEmp;
	}

	
	public Set<TrainningEmployee> getTrainEmp() {
		return trainEmp;
	}

	public void setTrainEmp(Set<TrainningEmployee> trainEmp) {
		this.trainEmp = trainEmp;
	}

}
