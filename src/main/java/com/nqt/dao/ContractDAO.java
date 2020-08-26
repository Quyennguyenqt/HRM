package com.nqt.dao;

import java.util.List;

import com.nqt.entities.Contract;

public interface ContractDAO {
	public void addContract(Contract contract);
	public void updateContract(Contract contract);
	public List<Contract> listContract();
	public Contract getContractById(Long id);
	public void removeContract(Contract contract);
}
