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

import com.nqt.dao.DepartmentDAO;
import com.nqt.entities.Department;

@Repository
@Transactional
public class DepartmentDAOImpl implements DepartmentDAO {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addDepartment(Department depart) {
		Session session = this.sessionFactory.getCurrentSession();
		if (getDepartmentById(depart.getDepartmentId()) == null) {
			session.persist(depart);
			logger.info("Department saved sucsessfull" + depart);
		}

	}

	@Override
	public void updateDepartment(Department depart) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(depart);
		logger.info("Department update successfull" + depart);


	}

	@Override
	public List<Department> listDepartment() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Department> departList = session.createQuery("from Department").list();
		for (Department depart : departList) {
			logger.info("Department list:" + depart);
		}
		return departList;
	}

	@Override
	public Department getDepartmentById(Long id) {
		Department depart = null;
		try {
			depart = findByDepartment(id).get(0);
			logger.info("Department loaded successfully, Department details= " + depart);
		} catch (Exception e) {
			e.getMessage();
		}

		return depart;
	}

	@SuppressWarnings("unchecked")
	private List<Department> findByDepartment(Long id) {
		List<Department> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from Department e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.departmentId = :departmentId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("departmentId", id);
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
	public void removeDepartment(Department depart) {
		Session session = this.sessionFactory.getCurrentSession();
		Department p = (Department) session.load(Department.class, new Long(depart.getDepartmentId()));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Department deleted successfully, Department details=" + p);

	}

}
