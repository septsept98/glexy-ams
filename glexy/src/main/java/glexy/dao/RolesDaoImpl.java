package glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;
import glexy.model.Roles;

public class RolesDaoImpl extends BaseDaoImpl<Roles> implements RolesDao {

	@Override
	public List<Roles> findAll() throws Exception {
		
		return getAll();
	}

	@Override
	public Roles findById(String id) throws Exception {
		
		return getById(id);
	}

	@Override
	public Roles saveOrUpdate(Roles roles) throws Exception {
		
		return null;
	}

	@Override
	public boolean deleteById(String id) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

}
