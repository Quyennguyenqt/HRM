package com.nqt.dao;

import java.util.List;

import com.nqt.entities.Training;

public interface TrainningDAO {
	public void addTraining(Training trainning);

	public void updateTraining(Training trainning);

	public List<Training> listTraining();

	public Training getTrainingById(Long id);

	public void removeTraining(Training trainning);
}
