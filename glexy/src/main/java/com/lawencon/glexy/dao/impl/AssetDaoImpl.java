package com.lawencon.glexy.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.dao.AssetDao;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.StatusAsset;

@Repository
public class AssetDaoImpl extends BaseDaoImpl<Asset> implements AssetDao{

	@Override
	public Asset saveOrUpdate(Asset data) throws Exception {
		return save(data);
	}

	@Override
	public boolean removeById(String id) throws Exception {
		return deleteById(id);
	}

	@Override
	public Asset findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public List<Asset> findAll() throws Exception {
		return getAll();
	}

	@Override
	public List<Asset> findByInvent(String idInvent) throws Exception {
		List<Asset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id ");
		sql.append("FROM assets ");
		sql.append("WHERE inventory_id=:idInvent");
		List<?> result = createNativeQuery(sql.toString())
				.setParameter("idInvent", idInvent)
				.getResultList();

		result.forEach(rs -> {
			Asset asset = new Asset();
			asset.setId(rs.toString());
			asset = getById(asset.getId());
			
			listResult.add(asset);
		});
		return listResult;
	}
  
	public List<Asset> findByBrandId(String brandId) throws Exception {
		List<Asset> resultList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ass ");
		sb.append("FROM Asset as ass ");
		sb.append("INNER JOIN FETCH ass.invoiceId ");
		sb.append("INNER JOIN FETCH ass.companyId as com ");
		sb.append("INNER JOIN FETCH ass.assetImg ");
		sb.append("INNER JOIN FETCH ass.assetTypeId ");
		sb.append("INNER JOIN FETCH ass.inventoryId ");
		sb.append("INNER JOIN FETCH ass.statusAssetId ");
		sb.append("INNER JOIN FETCH ass.brandId as b ");
		sb.append("WHERE b.id = :branId ");
		
		String sql = sb.toString();
		resultList = createQuery(sql, Asset.class)
				.setParameter("branId", brandId)
				.getResultList();
		return resultList;
	}
	
	@Override
	public List<Asset> findByCompanyId(String companyId) throws Exception {
		List<Asset> resultList = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ass ");
		sb.append("FROM Asset as ass ");
		sb.append("INNER JOIN FETCH ass.invoiceId ");
		sb.append("INNER JOIN FETCH ass.companyId as com ");
		sb.append("INNER JOIN FETCH ass.assetImg ");
		sb.append("INNER JOIN FETCH ass.assetTypeId ");
		sb.append("INNER JOIN FETCH ass.inventoryId ");
		sb.append("INNER JOIN FETCH ass.statusAssetId ");
		sb.append("INNER JOIN FETCH ass.brandId as b ");
		sb.append("WHERE com.id = :comId ");
		
		String sql = sb.toString();
		resultList = createQuery(sql, Asset.class)
				.setParameter("comId", companyId)
				.getResultList();

		return resultList;
	}

	@Override
	public List<Asset> findAllDeployAsset() throws Exception {
		List<Asset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id ");
		sql.append("FROM assets a ");
		sql.append("INNER JOIN status_assets sa ON a.status_asset_id = sa.id ");
		sql.append("WHERE sa.code_status_asset='SA1' ");
		sql.append("ORDER BY a.code");
		List<?> result = createNativeQuery(sql.toString())
				.getResultList();

		result.forEach(rs -> {
			Asset asset = new Asset();
			asset.setId(rs.toString());
			asset = getById(asset.getId());
			
			listResult.add(asset);
		});
		return listResult;
	}

	@Override
	public List<Asset> findAllGeneralAsset() throws Exception {
		List<Asset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.id ");
		sql.append("FROM assets a ");
		sql.append("INNER JOIN asset_types at ON a.asset_type_id = at.id ");
		sql.append("WHERE at.code='GNR' ");
		sql.append("ORDER BY a.code");
		List<?> result = createNativeQuery(sql.toString())
				.getResultList();

		result.forEach(rs -> {
			Asset asset = new Asset();
			asset.setId(rs.toString());
			asset = getById(asset.getId());
			
			listResult.add(asset);
		});
		return listResult;
	}

	@Override
	public List<Asset> findByAssetTypeId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT asset_type_id FROM assets ");
		sql.append("WHERE asset_type_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Asset> resultAsset = new ArrayList<>();

		result.forEach(rs -> {

			Asset data = new Asset();

			AssetType assetType = new AssetType();
			assetType.setId(rs.toString());
			data.setAssetTypeId(assetType);
			resultAsset.add(data);
		});

		return resultAsset;
	}

	@Override
	public List<Asset> findExpiredAsset() throws Exception {
		List<Asset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM assets ");
		sql.append("WHERE expired_date <= now() ");
		sql.append("ORDER BY expired_date ASC");
		List<?> result = createNativeQuery(sql.toString())
				.getResultList();

		result.forEach(rs -> {
			Asset asset = new Asset();
			asset.setId(rs.toString());
			asset = getById(asset.getId());
			
			listResult.add(asset);
		});
		return listResult;
	}
  
	public List<Asset> findByInventoryId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT asset_type_id FROM assets ");
		sql.append("WHERE inventory_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Asset> resultAsset = new ArrayList<>();

		result.forEach(rs -> {

			Asset data = new Asset();

			AssetType assetType = new AssetType();
			assetType.setId(rs.toString());
			data.setAssetTypeId(assetType);
			resultAsset.add(data);
		});

		return resultAsset;
	}

	@Override
	public List<Asset> findByInvoiceId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT asset_type_id FROM assets ");
		sql.append("WHERE invoice_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Asset> resultAsset = new ArrayList<>();

		result.forEach(rs -> {

			Asset data = new Asset();

			AssetType assetType = new AssetType();
			assetType.setId(rs.toString());
			data.setAssetTypeId(assetType);
			resultAsset.add(data);
		});

		return resultAsset;
	}

	@Override
	public List<Asset> findByStatusAssetId(String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT status_asset_id FROM assets ");
		sql.append("WHERE status_asset_id = :id");
		List<?> result = createNativeQuery(sql.toString()).setParameter("id", id).getResultList();

		List<Asset> resultAsset = new ArrayList<>();

		result.forEach(rs -> {

			Asset data = new Asset();

			StatusAsset statusAsset = new StatusAsset();
			statusAsset.setId(rs.toString());
			data.setStatusAssetId(statusAsset);
			resultAsset.add(data);
		});

		return resultAsset;
	}

	@Override
	public List<Asset> searchAssetGeneral(String search) throws Exception {
		List<Asset> listResult = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("Select id ");
		sql.append("FROM assets ");
		sql.append("WHERE status_asset_id = (SELECT id FROM asset_types WHERE lower(names) = 'general') AND ");
		sql.append("(lower(names) LIKE lower('%" + search + "%') OR lower(code) LIKE lower('%" + search + "%'))");

		List<?> result = createNativeQuery(sql.toString()).getResultList();

		result.forEach(rs -> {
			Asset asset = new Asset();
			asset.setId(rs.toString());
			asset = getById(asset.getId());

			listResult.add(asset);
		});

		return listResult;
	}

}
