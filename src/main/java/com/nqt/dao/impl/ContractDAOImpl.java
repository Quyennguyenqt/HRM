package com.nqt.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.ContractDAO;
import com.nqt.entities.Contract;

@Repository
@Transactional
public class ContractDAOImpl implements ContractDAO {

	private static final Logger logger = LoggerFactory.getLogger(ContractDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addContract(Contract contract) {
		Session session = this.sessionFactory.getCurrentSession();
		if (getContractById(contract.getContractId()) == null) {
			session.persist(contract);
			logger.info("Contract saved sucsessfull" + contract);
		}

	}

	@Override
	public void updateContract(Contract contract) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(contract);
		logger.info("Contract update successfull" + contract);
	}

	@Override
	public List<Contract> listContract() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Contract> contractList = session.createQuery("from Contract").list();
		for (Contract contract : contractList) {
			logger.info("Contract list:" + contract);
		}
		return contractList;
	}

	@Override
	public Contract getContractById(Long id) {
		Contract contract = null;
		try {
			contract = findByContract(id).get(0);
			logger.info("Contract loaded successfully, Contract details= " + contract);
		} catch (Exception e) {
			e.getMessage();
		}

		return contract;
	}

	@SuppressWarnings("unchecked")
	private List<Contract> findByContract(Long id) {
		List<Contract> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from Contract e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.contractId = :contractId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("contractId", id);
				list = query.getResultList();
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw new RuntimeException(e);
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		return list;
	}

	@Override
	public void removeContract(Contract contract) {
		Session session = this.sessionFactory.getCurrentSession();
		Contract p = (Contract) session.load(Contract.class, new Long(contract.getContractId()));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Contract deleted successfully, contract details=" + p);
	}

}
