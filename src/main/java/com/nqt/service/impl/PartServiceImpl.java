package com.nqt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.PartDAO;
import com.nqt.entities.Part;
import com.nqt.service.PartService;

@Service
@Transactional
public class PartServiceImpl implements PartService {
	
	@Autowired
	PartDAO partDAO;
	
	@Override
	public void addPart(Part part) {
		partDAO.addPart(part);
		
	}

	@Override
	public void updatePart(Part part) {
		partDAO.updatePart(part);
		
	}

	@Override
	public List<Part> listPart() {
		return partDAO.listPart();
	}

	@Override
	public Part getPartById(Long id) {
		return partDAO.getPartById(id);
	}

	@Override
	public void removePart(Part part) {
		partDAO.removePart(part);
		
	}

	@Override
	public boolean save(Part part, boolean b) {
		if (checkId(part, b)) {
			return true;
		}
		return false;
	}

	private boolean checkId(Part part, boolean b) {
		Part rl = getPartById(part.getPartId());
		if (rl == null && b == true) {
			addPart(part);
			return true;
		} else if (rl != null && b == false) {
			updatePart(part);
			return true;
		}
		return false;
	}

}
