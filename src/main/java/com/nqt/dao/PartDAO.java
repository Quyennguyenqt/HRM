package com.nqt.dao;

import java.util.List;

import com.nqt.entities.Part;

public interface PartDAO {
	public void addPart(Part part);
	public void updatePart(Part part);
	public List<Part> listPart();
	public Part getPartById(Long id);
	public void removePart(Part part);
}
