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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aht_training")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Training implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "traning_id")
	private Long trainingId;

	@Column(name = "training_code")
	private String trainingCode;

	@Column(name = "training_name")
	private String trainingName;

	@Column(name = "training_start_date")
	private Date trainingStartDate;

	@Column(name = "training_start_end")
	private Date trainingStartEnd;

	@Column(name = "training_status")
	private Integer traningStatus;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "trainingId")
	private Set<TrainningEmployee> trainEmp = new HashSet<TrainningEmployee>(0);

	public Training() {
	}

	public Training(Long trainingId, String trainningCode, String trainningName, Integer traningStatus,
			Set<TrainningEmployee> trainEmp) {
		this.trainingId = trainingId;
		this.trainingCode = trainningCode;
		this.traningStatus = traningStatus;
		this.trainEmp = trainEmp;

	}

	public Long getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Long trainingId) {
		this.trainingId = trainingId;
	}

	public String getTrainingCode() {
		return trainingCode;
	}

	public void setTrainingCode(String trainingCode) {
		this.trainingCode = trainingCode;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public Date getTrainingStartDate() {
		return trainingStartDate;
	}

	public void setTrainingStartDate(Date trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
	}

	public Date getTrainingStartEnd() {
		return trainingStartEnd;
	}

	public void setTrainingStartEnd(Date trainingStartEnd) {
		this.trainingStartEnd = trainingStartEnd;
	}

	public Integer getTraningStatus() {
		return traningStatus;
	}

	public void setTraningStatus(Integer traningStatus) {
		this.traningStatus = traningStatus;
	}

	public Set<TrainningEmployee> getTrainEmp() {
		return trainEmp;
	}

	public void setTrainEmp(Set<TrainningEmployee> trainEmp) {
		this.trainEmp = trainEmp;
	}

}
