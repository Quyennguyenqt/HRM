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
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "aht_training_emp", uniqueConstraints = { @UniqueConstraint(columnNames = { "emp_id", "training_id" }) })
public class TrainningEmployee implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "training_emp_id")
	private Long trainingEmpId;
	
	@Column(name = "training_emp_start_date")
	private Date trainingEmpStartDate;
	
	@Column(name = "training_emp_start_end")
	private Date trainingEmpStartEnd;
	
	@Column(name = "training_emp_status")
	private Integer trainingEmpStatus;
	
	@Column(name = "training_emp_result")
	private String trainingEmpResult;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id")
	private Employee emTrainId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_id")
	//@JsonProperty(access = Access.WRITE_ONLY)
	private Training trainingId;
	
	
	public TrainningEmployee() {}
	
	public TrainningEmployee(Integer trainingEmpStatus, Employee employeeId, Training trainingId) {
		this.trainingEmpStatus = trainingEmpStatus;
		this.setEmTrainId(employeeId);
		this.setTrainingId(trainingId);
	}
	public Long getTrainingEmpId() {
		return trainingEmpId;
	}

	public void setTrainingEmpId(Long trainingEmpId) {
		this.trainingEmpId = trainingEmpId;
	}

	public Date getTrainingEmpStartDate() {
		return trainingEmpStartDate;
	}

	public void setTrainingEmpStartDate(Date trainingEmpStartDate) {
		this.trainingEmpStartDate = trainingEmpStartDate;
	}

	public Date getTrainingEmpStartEnd() {
		return trainingEmpStartEnd;
	}

	public void setTrainingEmpStartEnd(Date trainingEmpStartEnd) {
		this.trainingEmpStartEnd = trainingEmpStartEnd;
	}

	public Integer getTrainingEmpStatus() {
		return trainingEmpStatus;
	}

	public void setTrainingEmpStatus(Integer trainingEmpStatus) {
		this.trainingEmpStatus = trainingEmpStatus;
	}

	public String getTrainingEmpResult() {
		return trainingEmpResult;
	}

	public void setTrainingEmpResult(String trainingEmpResult) {
		this.trainingEmpResult = trainingEmpResult;
	}

	public Employee getEmTrainId() {
		return emTrainId;
	}

	public void setEmTrainId(Employee emTrainId) {
		this.emTrainId = emTrainId;
	}

	public Training getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Training trainingId) {
		this.trainingId = trainingId;
	}

}
