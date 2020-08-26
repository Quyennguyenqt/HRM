package com.nqt.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.TrainningDAO;
import com.nqt.entities.Training;
import com.nqt.service.TrainingService;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	TrainningDAO trainDAO;
	
	@Override
	public void addTraining(Training trainning) {
		Calendar c = Calendar.getInstance();
		trainning.setTrainingStartDate(c.getTime());
		c.add(Calendar.MONTH, 2);
		trainning.setTrainingStartEnd(c.getTime());
		trainDAO.addTraining(trainning);
		
	}

	@Override
	public void updateTraining(Training trainning) {
		Calendar c = Calendar.getInstance();
		trainning.setTrainingStartDate(c.getTime());
		c.add(Calendar.MONTH, 2);
		trainning.setTrainingStartEnd(c.getTime());
		trainDAO.updateTraining(trainning);
		
	}

	@Override
	public List<Training> listTraining() {
		return trainDAO.listTraining();
	}

	@Override
	public Training getTrainingById(Long id) {
		return trainDAO.getTrainingById(id);
	}

	@Override
	public void removeTraining(Training trainning) {
		trainDAO.removeTraining(trainning);
		
	}

	@Override
	public boolean save(Training trainning, boolean b) {
		if (checkId(trainning, b)) {
			return true;
		}
		return false;
	}

	private boolean checkId(Training trainning, boolean b) {
		Training rl = getTrainingById(trainning.getTrainingId());
		if (rl == null && b == true) {
			addTraining(trainning);
			return true;
		} else if (rl != null && b == false) {
			updateTraining(trainning);
			return true;
		}
		return false;
	}


}
