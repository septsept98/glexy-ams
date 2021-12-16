package com.lawencon.glexy.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.BaseDaoImpl;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.AssetType;
import com.lawencon.glexy.model.Brand;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;
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
	public List<Asset> findByBrandId(String brandId) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT as.id, as.names, as.code, as.expired_date, as.asset_img, as.invoice_id, invo.code, as.company_id, ");
		sb.append("cmp.names, as.inventory_id, inven.code, inven.stock as.status_asset_id, sa.names, as.brand_id, br.names, ");
		sb.append("as.asset_type_id, at.names, at.code, as.is_active ");
		sb.append("FROM assets as ");
		sb.append("INNER JOIN files fl ON fl.id = as.asset_img ");
		sb.append("INNER JOIN invoices invo ON invo.id = as.invoice_id ");
		sb.append("INNER JOIN companies cmp ON cmd.id = as.company_id ");
		sb.append("INNER JOIN inventories inven ON inven.id = as.inventory_id ");
		sb.append("INNER JOIN status_assets sa ON sa.id = as.status_asset_id ");
		sb.append("INNER JOIN brand br ON br.id = as.brand_id ");
		sb.append("INNER JOIN asset_types at ON at.id = as.asset_type_id ");
		sb.append("WHERE as.brand_id = :brand_id");
		String sql = sb.toString();
		List<Asset> resultList = new ArrayList<>();
		
		try {
			List<?> result = createNativeQuery(sql)
							.setParameter("brand_id", brandId)
							.getResultList();
			
			result.forEach(rs -> {
				Object[] objArr = (Object[]) rs;
				Asset asset = new Asset();
				File file = new File();
				Invoice invoice = new Invoice();
				Company company = new Company();
				Inventory inventory = new Inventory();
				StatusAsset statusAsset = new StatusAsset();
				Brand brand = new Brand();
				AssetType assetType = new AssetType();
				
				asset.setId(objArr[0].toString());
				asset.setNames(objArr[1].toString());
				asset.setCode(objArr[2].toString());
				asset.setExpiredDate((LocalDate) objArr[3]);
				
				file.setId(objArr[4].toString());
				asset.setAssetImg(file);
				
				invoice.setId(objArr[5].toString());
				invoice.setCode(objArr[6].toString());
				asset.setInvoiceId(invoice);
				
				company.setId(objArr[7].toString());
				company.setNames(objArr[87].toString());
				asset.setCompanyId(company);
				
				inventory.setId(objArr[9].toString());
				inventory.setCode(objArr[10].toString());
				inventory.setStock(Integer.valueOf(objArr[11].toString()));
				asset.setInventoryId(inventory);
				
				statusAsset.setId(objArr[12].toString());
				statusAsset.setNameStatusAsset(objArr[13].toString());
				asset.setStatusAssetId(statusAsset);
				
				
				brand.setId(objArr[14].toString());
				brand.setNames(objArr[15].toString());
				asset.setBrandId(brand);
				
				assetType.setId(objArr[16].toString());
				assetType.setNames(objArr[17].toString());
				assetType.setCode(objArr[18].toString());
				asset.setAssetTypeId(assetType);

				asset.setIsActive(Boolean.valueOf(objArr[19].toString()));
				
				resultList.add(asset);
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		sb.append("WHERE com.id = :com.id ");
		
		String sql = sb.toString();
		resultList = createQuery(sql, Asset.class)
				.getResultList();

		
		return resultList;
	}
	

	
	
	

	
	
	


	
	
	
}
