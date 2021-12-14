package com.lawencon.glexy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseServiceImpl;
import com.lawencon.glexy.dao.UsersDao;
import com.lawencon.glexy.model.Users;

@Service
public class UsersServiceImpl extends BaseServiceImpl implements UsersService {
	
	@Autowired
	private UsersDao usersDao;
	
	
	@Override
	public List<Users> findAll() throws Exception {
		
		return usersDao.findAll();
	}

	@Override
	public Users findById(String id) throws Exception {
	
		return usersDao.findById(id);
	}

	@Override
	public Users saveOrUpdate(Users data) throws Exception {
		try {
			if(data.getId() != null) {
				Users users = usersDao.findById(data.getId());
				data.setId(users.getId());
				data.setEmail(users.getEmail());
				data.setPass(users.getPass());
				data.setCreatedAt(data.getCreatedAt());
				data.setCreatedBy(users.getCreatedBy());
				data.setIsActive(users.getIsActive());
				data.setVersion(users.getVersion());
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		return usersDao.saveOrUpdate(data);
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		
		return usersDao.deleteById(id);
	}

	@Override
	public Users getEmail(String email) throws Exception {
		
		return usersDao.getEmail(email);
	}

	
	
}
