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

import com.nqt.dao.EmployeeDAO;
import com.nqt.entities.ContracEmp;
import com.nqt.entities.Employee;
import com.nqt.entities.TrainningEmployee;

@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Long addEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		if (getEmployeeById(employee.getEmpId()) == null) {
			session.persist(employee);
			logger.info("employee saved sucsessfull" + employee);
		}
		return employee.getEmpId();
	}

	@Override
	public void updateEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(employee);
		logger.info("employee update successfull" + employee);

	}

	@Override
	public List<Employee> listEmployee() {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Employee> empList = session.createQuery("from Employee").list();
		for (Employee employee : empList) {
			logger.info("Employee list:" + employee);
		}
		return empList;
	}

	@Override
	public Employee getEmployeeById(Long id) {
		Employee employee = null;
		try {
			employee = findByEmployee(id).get(0);
			logger.info("employee loaded successfully, employee details= " + employee);
		} catch (Exception e) {
			e.getMessage();
		}

		return employee;
	}

	@SuppressWarnings("unchecked")
	private List<Employee> findByEmployee(Long id) {
		List<Employee> list = null;
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("select e from Employee e where 1=1 ");
			if (id != null) {
				queryStr.append("and e.empId = :empId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("empId", id);
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
	public void removeEmployee(Employee employee) {
//		Session session = this.sessionFactory.getCurrentSession();
//		Employee p = (Employee) session.load(Employee.class, new Long(employee.getEmpId()));
//		if (null != p) {
//			session.delete(p);
//		}
//		logger.info("employee deleted successfully, employee details=" + p);

		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("Delete from Employee c where 1=1 and ");
			if (employee.getEmpId() != null) {
				queryStr.append("c.empId = :empId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("empId", employee.getEmpId());
				query.executeUpdate();
				transaction.commit();
			} catch (Exception e) {
				e.getMessage();
				transaction.rollback();
				throw new RuntimeException(e);
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}
		logger.info("employee deleted successfully, employee details");
	}

	@Override
	public void addContractEmp(ContracEmp conEmp) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(conEmp);
		logger.info("ContracEmp save successfully, ContracEmp Details=" + conEmp);

	}

	@Override
	public void removeContractEmp(Long id) {

		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("Delete from ContracEmp c where 1=1 and ");
			if (id != null) {
				queryStr.append("c.employeeId.empId = :employeeId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("employeeId", id);
				query.executeUpdate();
				transaction.commit();
			} catch (Exception e) {
				e.getMessage();
				transaction.rollback();
				throw new RuntimeException(e);
			}
		} finally {
			session.close();
			ManagedSessionContext.unbind(sessionFactory);
		}

	}

	@Override
	public void updateEmpTrain(TrainningEmployee tremp) {
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("UPDATE TrainningEmployee e SET ");
			if (tremp.getTrainingEmpResult() != null) {
				queryStr.append(" e.trainingEmpResult = :trainingEmpResult");
			}
			if (tremp.getTrainingEmpStartDate() != null) {
				queryStr.append(", e.trainingEmpStartDate = :trainingEmpStartDate");
			}
			if (tremp.getTrainingEmpStartEnd() != null) {
				queryStr.append(", e.trainingEmpStartEnd = :trainingEmpStartEnd");
			}
			if (tremp.getTrainingEmpStatus() != null) {
				queryStr.append(", e.trainingEmpStatus = :trainingEmpStatus");
			}
			if (tremp.getEmTrainId() != null) {
				queryStr.append(" where e.emTrainId =: emTrainId");
			}
			if (tremp.getTrainingId() != null) {
				queryStr.append(" and e.trainingId =: trainingId");
			}

			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("trainingEmpResult", tremp.getTrainingEmpResult());
				query.setParameter("trainingEmpStartDate", tremp.getTrainingEmpStartDate());
				query.setParameter("trainingEmpStartEnd", tremp.getTrainingEmpStartEnd());
				query.setParameter("trainingEmpStatus", tremp.getTrainingEmpStatus());
				query.setParameter("emTrainId", tremp.getEmTrainId());
				query.setParameter("trainingId", tremp.getTrainingId());
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
		logger.info("TrainningEmployee save successfully, TrainningEmployee Details=" + tremp);

	}

	@Override
	public void updateContractEmp(ContracEmp contrEmp) {
		Session session = sessionFactory.openSession();
		try {
			ManagedSessionContext.bind(session);
			Transaction transaction = session.beginTransaction();
			StringBuilder queryStr = new StringBuilder();
			queryStr.append("UPDATE ContracEmp e SET ");
			if (contrEmp.getContractEmpStartDate() != null) {
				queryStr.append("  e.contractEmpStartDate = :contractEmpStartDate");
			}
			if (contrEmp.getContractEmpEndDate() != null) {
				queryStr.append(",e.contractEmpEndDate = :contractEmpEndDate");
			}
			if (contrEmp.getContractEmpStatus() != null) {
				queryStr.append(", e.contractEmpStatus = :contractEmpStatus");
			}
			if (contrEmp.getContractId() != null) {
				queryStr.append(" where e.contractId = :contractId");
			}
			if (contrEmp.getEmpConId() != null) {
				queryStr.append(" and e.empConId =: empConId");
			}
			try {
				Query query = session.createQuery(queryStr.toString());
				query.setParameter("contractEmpStartDate", contrEmp.getContractEmpStartDate());
				query.setParameter("contractEmpEndDate", contrEmp.getContractEmpEndDate());
				query.setParameter("contractEmpStatus", contrEmp.getContractEmpStatus());
				query.setParameter("contractId", contrEmp.getContractId());
				query.setParameter("empConId", contrEmp.getEmpConId());
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
		logger.info("ContracEmp save successfully, ContracEmp Details=" + contrEmp);

	}

	@Override
	public void addTrainEmp(TrainningEmployee trainEmp) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(trainEmp);
		logger.info("TrainningEmployee save successfully, TrainningEmployee Details=" + trainEmp);

	}

}
