package com.nqt.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "aht_contract_emp")
public class ContracEmp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_emp_id")
	private Long contractEmpId;
	
	@Column(name = "contract_emp_start_date")
	private Date contractEmpStartDate;
	
	@Column(name = "contract_emp_end_date")
	private Date contractEmpEndDate;
	
	@Column(name = "contract_emp_status")
	private Integer contractEmpStatus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contract_id")
	//@JsonProperty(access = Access.WRITE_ONLY)
	private Contract contractId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id")
	//@JsonIgnore
	private Employee empConId;
	
	public ContracEmp() {}
	
	public ContracEmp(Contract contractId,Employee employeeId) {
		this.contractId = contractId;
		 this.setEmpConId(employeeId);
	}
	
	
	public Long getContractEmpId() {
		return contractEmpId;
	}

	public void setContractEmpId(Long contractEmpId) {
		this.contractEmpId = contractEmpId;
	}

	public Date getContractEmpStartDate() {
		return contractEmpStartDate;
	}

	public void setContractEmpStartDate(Date contractEmpStartDate) {
		this.contractEmpStartDate = contractEmpStartDate;
	}

	public Date getContractEmpEndDate() {
		return contractEmpEndDate;
	}

	public void setContractEmpEndDate(Date contractEmpEndDate) {
		this.contractEmpEndDate = contractEmpEndDate;
	}

	public Integer getContractEmpStatus() {
		return contractEmpStatus;
	}

	public void setContractEmpStatus(Integer contractEmpStatus) {
		this.contractEmpStatus = contractEmpStatus;
	}

	public Contract getContractId() {
		return contractId;
	}

	public void setContractId(Contract contractId) {
		this.contractId = contractId;
	}

	public Employee getEmpConId() {
		return empConId;
	}

	public void setEmpConId(Employee empConId) {
		this.empConId = empConId;
	}

	

	
	

}
