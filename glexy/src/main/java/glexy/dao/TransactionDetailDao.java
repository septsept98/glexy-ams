package glexy.dao;

import java.util.List;

import glexy.model.TransactionDetail;


public interface TransactionDetailDao {

	List<TransactionDetail> findAll() throws Exception;

	TransactionDetail findById(String id) throws Exception;

	TransactionDetail saveOrUpdate(TransactionDetail data) throws Exception;

}
