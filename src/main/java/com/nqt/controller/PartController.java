package com.nqt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Part;
import com.nqt.service.PartService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class PartController {
	@Autowired
	PartService partService;

	@RequestMapping(value = "/parts", method = RequestMethod.GET)
	public ResponseEntity<List<Part>> findAllProduct() {
		List<Part> parts = partService.listPart();
		if (parts.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(parts, HttpStatus.OK);
	}

	@RequestMapping(value = "/parts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Part> getPartById(@PathVariable("id") Long id) {
		Part part = partService.getPartById(id);
		if (part == null) {
			return new ResponseEntity<>(part, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(part, HttpStatus.OK);
	}

	@RequestMapping(value = "/parts", method = RequestMethod.POST)
	public ResponseEntity<Part> createPart(@RequestBody Part part) {
		if (partService.save(part, true)) {
			return new ResponseEntity<>(part, HttpStatus.CREATED);
		}
		return new ResponseEntity<Part>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/parts/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Part> updatePart(@PathVariable("id")Long id, @RequestBody Part part) {
			part.setPartId(id);
		if (partService.save(part, false)) {
			return new ResponseEntity<>(part, HttpStatus.OK);
		}
		return new ResponseEntity<Part>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/parts/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Part> deletePart(@PathVariable("id") Long id) {
		Part partCurretn = partService.getPartById(id);
		if (partCurretn == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		partService.removePart(partCurretn);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
