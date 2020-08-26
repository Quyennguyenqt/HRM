package com.nqt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nqt.entities.Contract;
import com.nqt.service.ContractService;

@RestController
//@RequestMapping("api/contracts")
public class ContractController {

	@Autowired
	ContractService contractService;

	@RequestMapping(value = "/contracts", method = RequestMethod.GET)
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<Contract>> findAllContract() {
		List<Contract> contracts = contractService.listContract();
		if (contracts.isEmpty()) {
			return new ResponseEntity<List<Contract>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Contract>>(contracts, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Contract> findById(@PathVariable("id") Long id) {
		Contract contractCurr = contractService.getContractById(id);
		if (contractCurr == null) {
			return new ResponseEntity<Contract>( HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Contract>(contractCurr, HttpStatus.OK);
	}

	@RequestMapping(value = "/contracts", method = RequestMethod.POST)
	public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//Optional<User>  user = userRepository.findByUsername(authentication.getName());
		if (contractService.save(contract, true)) {
			return new ResponseEntity<Contract>(contract, HttpStatus.CREATED);
		}
		return new ResponseEntity<Contract>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/contracts", method = RequestMethod.PUT)
	public ResponseEntity<Contract> updateContract(@RequestBody Contract contract) {
		if (contractService.save(contract, false)) {
			return new ResponseEntity<Contract>(contract, HttpStatus.OK);
		}
		return new ResponseEntity<Contract>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/contracts", method = RequestMethod.DELETE)
	public ResponseEntity<Contract> deleteContract(@RequestBody Contract contract) {
		Optional<Contract> contractCurr = Optional.of(contractService.getContractById(contract.getContractId()));
		if (!contractCurr.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		contractService.removeContract(contractCurr.get());
		return new ResponseEntity<Contract>(HttpStatus.OK);
	}
}
