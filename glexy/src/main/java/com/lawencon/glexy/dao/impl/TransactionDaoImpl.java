package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.model.Transactions;

@Repository
public class TransactionDaoImpl extends BaseDaoImpl<Transactions> implements TransactionDao {

	@Override
	public List<Transactions> findAll() throws Exception {
		return getAll();
	}

	@Override
	public Transactions findById(String id) throws Exception {
		Transactions result = new Transactions();
		try {
			result = getById(id);
		} catch (NoResultException e) {
			throw new NoResultException("Transaction not found");
		}
		return result;
	}

	@Override
	public Transactions saveOrUpdate(Transactions data) throws Exception {
		return save(data);
	}

	@Override
	public List<Transactions> findByEmployeeId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT employee_id FROM transactions ");
		sql.append("WHERE employee_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Transactions> resultTransaction = new ArrayList<>();

		result.forEach(rs -> {

			Transactions data = new Transactions();

			Employee employee = new Employee();
			employee.setId(rs.toString());
			data.setEmployeeId(employee);
			resultTransaction.add(data);
		});

		return resultTransaction;
	}

	@Override
	public List<Transactions> findByUsersId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT employee_id FROM transactions ");
		sql.append("WHERE users_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Transactions> resultTransaction = new ArrayList<>();

		result.forEach(rs -> {

			Transactions data = new Transactions();

			Employee employee = new Employee();
			employee.setId(rs.toString());
			data.setEmployeeId(employee);
			resultTransaction.add(data);
		});

		return resultTransaction;
	}

	@Override
	public List<Transactions> findByLocationId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT employee_id FROM transactions ");
		sql.append("WHERE location_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Transactions> resultTransaction = new ArrayList<>();

		result.forEach(rs -> {

			Transactions data = new Transactions();

			Location location = new Location();
			location.setId(rs.toString());
			data.setLocationId(location);
			resultTransaction.add(data);
		});

		return resultTransaction;
	}

	@Override
	public List<Transactions> findAllNotCheckIn() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t.id ");
		sql.append("FROM transactions t ");
		sql.append("INNER JOIN transaction_details td ON td.transaction_id = t.id ");
		sql.append("WHERE td.date_checkin ISNULL ");
		sql.append("AND td.status_tr_checkin_id ISNULL ");
		sql.append("GROUP BY t.id ");
		sql.append("ORDER BY t.checkout_date ASC");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		List<Transactions> resultTransaction = new ArrayList<>();

		result.forEach(rs -> {
			Transactions data = getById(rs.toString());
			resultTransaction.add(data);
		});

		return resultTransaction;
	}

	@Override
	public List<Transactions> findAllCheckIn() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t.id ");
		sql.append("FROM transactions t ");
		sql.append("INNER JOIN transaction_details td ON td.transaction_id = t.id ");
		sql.append("WHERE td.date_checkin NOTNULL ");
		sql.append("AND td.status_tr_checkin_id NOTNULL ");
		sql.append("GROUP BY t.id");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		List<Transactions> resultTransaction = new ArrayList<>();

		result.forEach(rs -> {
			Transactions data = getById(rs.toString());
			resultTransaction.add(data);
		});

		return resultTransaction;
	}

	
}
