package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.model.Roles;
import com.lawencon.glexy.model.Users;

@Repository
public class UsersDaoImpl extends BaseDaoImpl<Users> implements UsersDao {

	@Override
	public List<Users> findAll() throws Exception {

		return getAll();
	}

	@Override
	public Users findById(String id) throws Exception {

		return getById(id);
	}

	@Override
	public Users saveOrUpdate(Users users) throws Exception {

		return save(users);
	}

	@Override
	public boolean deleteById(String id) throws Exception {

		return super.deleteById(id);
	}

	@Override
	public Users getEmail(String email) throws Exception {
		Users users = null;
		try {

			users = createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class)
					.setParameter("email", email).getSingleResult();

		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public Users getByNip(String nip) throws Exception {
		Users users = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT u FROM Users u ");
			sql.append("INNER JOIN FETCH u.employeeId AS e ");
			sql.append("WHERE e.nip = :nip");
			users = createQuery(sql.toString(), Users.class).setParameter("nip", nip).getSingleResult();

		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public List<Users> findByRolesId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT roles_id FROM users ");
		sql.append("WHERE roles_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Users> resultUsers = new ArrayList<>();

		result.forEach(rs -> {

			Users data = new Users();

			Roles roles = new Roles();
			roles.setId(rs.toString());
			data.setRolesId(roles);
			resultUsers.add(data);
		});

		return resultUsers;
	}

	@Override
	public List<Users> findByEmployeeId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT employee_id FROM users ");
		sql.append("WHERE employee_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Users> resultUsers = new ArrayList<>();

		result.forEach(rs -> {

			Users data = new Users();

			Employee employee = new Employee();
			employee.setId(rs.toString());
			data.setEmployeeId(employee);
			resultUsers.add(data);
		});

		return resultUsers;
	}

}
