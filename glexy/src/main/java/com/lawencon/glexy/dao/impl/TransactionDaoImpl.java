package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.TransactionDao;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Location;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.Transactions;
import com.lawencon.glexy.model.Users;

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

	
}
