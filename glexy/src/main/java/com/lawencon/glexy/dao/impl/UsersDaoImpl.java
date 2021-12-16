package com.lawencon.glexy.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.UsersDao;
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

}
