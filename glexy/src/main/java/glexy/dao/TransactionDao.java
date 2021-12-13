package glexy.dao;

import java.util.List;

import glexy.model.Transactions;

public interface TransactionDao {

	List<Transactions> findAll() throws Exception;

	Transactions findById(String id) throws Exception;

	Transactions saveOrUpdate(Transactions data) throws Exception;

}
