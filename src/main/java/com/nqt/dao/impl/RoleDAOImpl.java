package com.nqt.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
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

import com.nqt.dao.RoleDAO;
import com.nqt.entities.Role;
import com.nqt.entities.UserRole;

@Repository
@Transactional
public class RoleDAOImpl implements RoleDAO {

	private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addRole(Role role) {
		Session session = this.sessionFactory.getCurrentSession();
		if (getRoleById(role.getRoleId()) == null) {
			session.persist(role);
			logger.info("Role saved sucsessfull" + role);
		}

	}

	@Override
	public void updateRole(Role role) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(role);
		logger.info("Role update successfull" + role);

	}

	@Override
	public List<Role> listRole() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Role> roleList = session.createQuery("from Role").list();
		for (Role role : roleList) {
			logger.info("Role list:" + role);
		}
		return roleList;

	}

	@Override
	public void removeRole(Role role) {
		Session session = this.sessionFactory.getCurrentSession();
		Role p = (Role) session.load(Role.class, new Long(role.getRoleId()));
		if (null != p) {
			session.delete(p);
		}
		logger.info("Role deleted successfully, role details=" + p);
	}

	@Override
	public Role getRoleById(Long id) {
		Role role = null;
		try {
			role = findByRole(id).get(0);
			logger.info("Role loaded successfully, Role details= " + role);
		} catch (Exception e) {
			e.getMessage();
		}

		return role;
	}

	@SuppressWarnings("unchecked")
	private List<Role> findByRole(Long id) {
		List<Role> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from Role e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.roleId = :roleId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("roleId", id);
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

	@Autowired
	private EntityManager entityManager;

	public List<String> getRoleNames(Long userId) {
		String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " //
				+ " where ur.appUser.userId = :userId ";

		Query query = this.entityManager.createQuery(sql, String.class);
		query.setParameter("userId", userId);
		return query.getResultList();

	}

}