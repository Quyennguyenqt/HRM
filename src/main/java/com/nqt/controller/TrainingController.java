package com.nqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Training;
import com.nqt.service.TrainingService;

@RestController
public class TrainingController {

	@Autowired
	TrainingService trainService;

	@RequestMapping(value = "/trainings", method = RequestMethod.GET)
	public ResponseEntity<List<Training>> findAllTraining() {
		List<Training> train = trainService.listTraining();
		if (train.isEmpty()) {
			return new ResponseEntity<List<Training>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Training>>(train, HttpStatus.OK);
	}

	@RequestMapping(value = "/trainings/{id}", method = RequestMethod.GET)
	public ResponseEntity<Training> getById(@PathVariable("id") Long id) {
		Training train = trainService.getTrainingById(id);
		if (train == null) {
			return new ResponseEntity<Training>(train, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Training>(train, HttpStatus.OK);
	}

	@RequestMapping(value = "/trainings", method = RequestMethod.POST)
	public ResponseEntity<Training> createTraining(@RequestBody Training train) {
		if (trainService.save(train, true)) {
			return new ResponseEntity<Training>(train, HttpStatus.CREATED);
		}
		return new ResponseEntity<Training>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/trainings", method = RequestMethod.PUT)
	public ResponseEntity<Training> updateTraining(@RequestBody Training train) {
		if (trainService.save(train, false)) {
			return new ResponseEntity<Training>(train, HttpStatus.OK);
		}
		return new ResponseEntity<Training>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/trainings", method = RequestMethod.DELETE)
	public ResponseEntity<Training> deleteTraining(@RequestBody Training train) {
		Training trainCurr = trainService.getTrainingById(train.getTrainingId());
		if (trainCurr == null) {
			return new ResponseEntity<Training>(train, HttpStatus.NOT_FOUND);
		}
		trainService.removeTraining(trainCurr);
		return new ResponseEntity<Training>(trainCurr, HttpStatus.OK);
	}

}
