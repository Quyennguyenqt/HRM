package com.nqt.service;

import java.util.List;

import com.nqt.entities.Contract;

public interface ContractService {
	public void addContract(Contract contract);

	public void updateContract(Contract contract);

	public List<Contract> listContract();

	public Contract getContractById(Long id);

	public void removeContract(Contract contract);
	
	public boolean save(Contract contract, boolean b);
}
