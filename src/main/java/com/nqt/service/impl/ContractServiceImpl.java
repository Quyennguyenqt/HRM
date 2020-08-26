package com.nqt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.ContractDAO;
import com.nqt.entities.Contract;
import com.nqt.service.ContractService;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractDAO contractDAO;

	@Override
	public void addContract(Contract contract) {
		contractDAO.addContract(contract);

	}

	@Override
	public void updateContract(Contract contract) {
		contractDAO.updateContract(contract);

	}

	@Override
	public List<Contract> listContract() {
		return contractDAO.listContract();
	}

	@Override
	public Contract getContractById(Long id) {
		return contractDAO.getContractById(id);
	}

	@Override
	public void removeContract(Contract contract) {
		contractDAO.removeContract(contract);

	}

	@Override
	public boolean save(Contract contract, boolean b) {
		if (checkId(contract, b)) {
			return true;
		}
		return false;
	}

	public boolean checkId(Contract contract, boolean b) {
		Contract ct = getContractById(contract.getContractId());
		if (ct == null && b == true) {
			addContract(contract);
			return true;
		} else if (ct != null && b == false) {
			updateContract(contract);
			return true;
		}
		return false;
	}

}
