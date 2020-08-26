package com.nqt.service;

import java.util.List;

import com.nqt.entities.Training;

public interface TrainingService {
	
	public void addTraining(Training trainning);

	public void updateTraining(Training trainning);

	public List<Training> listTraining();

	public Training getTrainingById(Long id);

	public void removeTraining(Training trainning);
	
	public boolean save(Training trainning, boolean b);

}
