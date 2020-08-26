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

import com.nqt.dao.PartDAO;
import com.nqt.entities.Part;

@Repository
@Transactional
public class PartDAOImpl implements PartDAO {

	private static final Logger logger = LoggerFactory.getLogger(PartDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addPart(Part part) {
		Session session = this.sessionFactory.getCurrentSession();
		if (getPartById(part.getPartId()) == null) {
			session.persist(part);
			logger.info("Part saved sucsessfull" + part);
		}
	}

	@Override
	public void updatePart(Part part) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(part);
		logger.info("Part update successfull" + part);


	}

	@Override
	public List<Part> listPart() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Part> partList = session.createQuery("from Part").list();
		for (Part part : partList) {
			logger.info("Part list:" + part);
		}
		return partList;
	}

	@Override
	public Part getPartById(Long id) {
		Part part = null;
		try {
			part = findByPart(id).get(0);
			logger.info("Part loaded successfully, Role details= " + part);
		} catch (Exception e) {
			e.getMessage();
		}

		return part;
	}

	@SuppressWarnings("unchecked")
	private List<Part> findByPart(Long id) {
		List<Part> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from Part e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.partId = :partId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("partId", id);
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
	public void removePart(Part part) {
		Session session = this.sessionFactory.getCurrentSession();
		Part p = (Part) session.load(Part.class, new Long(part.getPartId()));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Part deleted successfully, part details=" + p);

	}

}
