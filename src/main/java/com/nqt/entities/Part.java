package com.nqt.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aht_parts")
public class Part {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parts_id", nullable = false)
	private Long partId;
	@Column(name = "parts_name")
	private String partName;
	public Long getPartId() {
		return partId;
	}
	public void setPartId(Long partId) {
		this.partId = partId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	
	
}
