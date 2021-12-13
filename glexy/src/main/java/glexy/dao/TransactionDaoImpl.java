package glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;

import glexy.model.Transactions;

public class TransactionDaoImpl extends BaseDaoImpl<Transactions> implements TransactionDao {

	@Override
	public List<Transactions> findAll() throws Exception {
		return getAll();
	}

	@Override
	public Transactions findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public Transactions saveOrUpdate(Transactions data) throws Exception {
		save(data);
		return null;
	}
}
