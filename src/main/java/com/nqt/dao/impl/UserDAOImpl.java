package com.nqt.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nqt.dao.UserDAO;
import com.nqt.entities.Role;
import com.nqt.entities.User;
import com.nqt.entities.UserRole;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public User findUserAccount(String userName) {
//		try {
//			String sql = "Select e from " + User.class.getName() + " e " //
//					+ " where e.userName = :userName ";
//
//			Query query = entityManager.createQuery(sql, User.class);
//			query.setParameter("userName", userName);
//
//			return (User) query.getSingleResult();
//		} catch (NoResultException e) {
		return null;
//		}
	}

	@Override
	public Long addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(user);
		logger.info("User save successfully, User Details=" + user);
		return user.getUserId();
	}

	@Override
	public void updateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);
		logger.info("User updated successfully, User Details=" + user);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUser() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userList = session.createQuery("from User").list();
		for (User u : userList) {
			logger.info("User List::" + u);
		}
		return userList;
	}

	@Override
	public User getUserById(Long id) {
		User user = null;
		try {
			user = findByUser(id).get(0);
			logger.info("User loaded successfully, User details=" + user);
		} catch (Exception e) {
			e.getMessage();
		}
		return user;
	}

	@Override
	public void removeUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.load(User.class, new Long(user.getUserId()));
		if (null != p) {
			session.delete(p);
		}
		logger.info("User deleted successfully, user details=" + p);

	}

	@Override
	public void addUserRole(UserRole userole) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			
			session.persist(userole);
		}catch (Exception e) {
			e.getMessage();
		}
		logger.info("Userole save successfully, UserRole Details=" + userole);

	}

	@Override
	public void updateUserRole(UserRole userole) {
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("UPDATE UserRole e SET e.role =: role where 1=1 ");
			if (userole.getUser() != null) {
				queryStr.append("and e.user = :user");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("role", userole.getRole()).setParameter("user", userole.getUser());
				query.executeUpdate();
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw new RuntimeException(e);
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		logger.info("Userole save successfully, UserRole Details=" + userole);

	}

	@SuppressWarnings("unchecked")
	private List<User> findByUser(Long id) {
		List<User> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from User e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.userId = :userId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("userId", id);
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

	@SuppressWarnings("unchecked")
	@Override
	public User existByUserName(String username) {
		List<User> list = null;
		User user = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("Select e from User e where 1 = 1");
			if (null != username || !("".equals(username))) {
				queryStr.append(" and e.username = :username");
			}

			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("username", username);
				list = query.getResultList();
				if (list.isEmpty()) {
					user = null;
				} else {
					user = list.get(0);
				}
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				e.getMessage();
				throw new RuntimeException();
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User existByEmail(String email) {
		List<User> list;
		User user = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("Select e from User e where 1 = 1");
			if (null != email || !("".equals(email))) {
				queryStr.append(" and e.email = :email");
			}

			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("email", email);
				list = query.getResultList();
				if (list.isEmpty()) {
					user = null;
				} else {
					user = list.get(0);
				}
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw new RuntimeException();
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByUserName(String username) {
		List<User> list;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("Select e from User e where 1 = 1");
			if (null != username || !("".equals(username))) {
				queryStr.append(" and e.userName = :userName");
			}

			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("userName", username);
				list = query.getResultList();
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw new RuntimeException();
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		return list;
	}

	@Override
	public List<Role> getRoleByUser(User user) {
		List<Role> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("Select e from UserRole e where 1=1 ");
			if (null != user) {
				queryStr.append("and e.user = :user");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("user", user);
				list = query.getResultList();
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				throw new RuntimeException();
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		return list;
	}

}

//	public List<User> findAllUser() {
//		List<User> list;
//		 String sql = "Select e from User e";
//		 try {
//			Query query = entityManager.createQuery(sql);
//			list = query.getResultList();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return list;
//	}
//	public List<User> findAllUser() {
//		List<User> list;
//		Session session = sessionFactory.openSession();
//		ManagedSessionContext.bind(session);
//		Transaction transaction = session.beginTransaction();
//		String sql = "Select e from User e";
//		try {
//			Query query = session.createQuery(sql);
//			list = query.getResultList();
//			transaction.commit();
//		} catch (Exception e) {
//			transaction.rollback();
//			throw new RuntimeException(e);
//		} finally {
//			session.close();
//			ManagedSessionContext.unbind(sessionFactory);
//		}
//		return list;
//	}
//
//	public void save(User user) {
//		Session session = this.sessionFactory.getCurrentSession();
//		UserRole ur = user.getUserRoles().get(0);
//		Role role = ur.getRole();
//		String st = role.getRoleName();
//		Long idrole = null;
//		if (st.equals("ADMIN")) {
//			idrole = 1L;
//		}
//		role.setRoleId(idrole);
//		Long id = (Long) session.save(user);
//		User us = new User();
//		us.setUserId(id);
//		ur.setUser(us);
//		ur.setRole(role);
//		session.save(ur);
//		
//		 
//		 
//	}
