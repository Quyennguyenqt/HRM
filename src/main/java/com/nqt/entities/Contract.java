package com.nqt.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "aht_contract")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contract implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private Long contractId;
	
	@Column(name = "contract_code")
	private String contractCode;
	
	@Column(name = "contract_type")
	private Integer contractType;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "contractId")
	private Set<ContracEmp> contracEmp = new HashSet<ContracEmp>(0);
	
	
	public Contract() {}
	
	public Contract(Long contractId, String contractCode, Integer contractType, Set<ContracEmp> contracEmp ) {
		this.contractId = contractId;
		this.contractCode = contractCode;
		this.contractType = contractType;
		this.contracEmp = contracEmp;
	}
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}
	@JsonIgnore
	public Set<ContracEmp> getContracEmp() {
		return contracEmp;
	}

	public void setContracEmp(Set<ContracEmp> contracEmp) {
		this.contracEmp = contracEmp;
	}

}
