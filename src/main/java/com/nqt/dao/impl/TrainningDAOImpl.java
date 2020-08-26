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

import com.nqt.dao.TrainningDAO;
import com.nqt.entities.Training;

@Repository
@Transactional
public class TrainningDAOImpl implements TrainningDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TrainningDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void addTraining(Training trainning) {
		Session session = this.sessionFactory.getCurrentSession();
		if (getTrainingById(trainning.getTrainingId()) == null) {
			session.persist(trainning);
			logger.info("Training saved sucsessfull" + trainning);
		}
		
	}

	@Override
	public void updateTraining(Training trainning) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(trainning);
		logger.info("Training update successfull" + trainning);
		
	}

	@Override
	public List<Training> listTraining() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Training> trainingList = session.createQuery("from Training").list();
		for (Training trainging : trainingList) {
			logger.info("Training list:" + trainging);
		}
		return trainingList;
	}

	@Override
	public Training getTrainingById(Long id) {
		Training training = null;
		try {
			training = findByTraining(id).get(0);
			logger.info("Training loaded successfully, Training details= " + training);
		} catch (Exception e) {
			e.getMessage();
		}

		return training;
	}

	@SuppressWarnings("unchecked")
	private List<Training> findByTraining(Long id) {
		List<Training> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from Training e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.trainingId = :trainingId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("trainingId", id);
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
	public void removeTraining(Training training) {
		Session session = this.sessionFactory.getCurrentSession();
		Training p = (Training) session.load(Training.class, new Long(training.getTrainingId()));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Training deleted successfully, Training details=" + p);
		
	}
	
}
