package com.nqt.service;

import java.util.List;

import com.nqt.entities.Part;

public interface PartService {
	public void addPart(Part part);

	public void updatePart(Part part);

	public List<Part> listPart();

	public Part getPartById(Long id);

	public void removePart(Part part);
	
	public boolean save(Part part, boolean b);
}
