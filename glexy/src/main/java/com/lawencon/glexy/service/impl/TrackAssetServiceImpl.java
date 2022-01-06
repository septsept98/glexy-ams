package com.lawencon.glexy.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.glexy.dao.TrackAssetDao;
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.email.EmailHandler;
import com.lawencon.glexy.helper.EmailHelper;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.model.Users;
import com.lawencon.glexy.service.TrackAssetService;
import com.lawencon.glexy.service.UsersService;
import com.lawencon.util.JasperUtil;

@Service
public class TrackAssetServiceImpl extends BaseGlexyServiceImpl implements TrackAssetService {
	
	@Autowired
	private TrackAssetDao trackAssetDao;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private EmailHandler emailHandler;

	@Override
	public List<TrackAsset> findAll() throws Exception {
		return trackAssetDao.findAll();
	}

	@Override
	public TrackAsset findById(String id) throws Exception {
		TrackAsset result = new TrackAsset();
		try {
			result = trackAssetDao.findById(id);
			return result;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NoResultException("Status Transaction not found");
		}
	}

	@Override
	public TrackAsset saveOrUpdate(TrackAsset data) throws Exception {
		try {
			trackAssetDao.saveOrUpdate(data);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
	}

	@Override
	public List<TrackAsset> findByAssetTr(String assetCode, String trCode) throws Exception {
		return trackAssetDao.findByAssetTr(assetCode, trCode);
	}

	@Override
	public List<TrackAsset> findByAsset(String assetCode) throws Exception {
		return trackAssetDao.findByAsset(assetCode);
	}

	@Override
	public byte[] pdfTrackAsset() throws Exception {
		Users users = usersService.findByIdAuth();
		Company company = users.getEmployeeId().getCompanyId();
		HashMap<String, Object> map = new HashMap<>();
		map.put("company", company.getNames());
		map.put("address", company.getAddress());
		map.put("website", company.getWebsite());
		map.put("telp", company.getPhoneNumber());
		map.put("fax", company.getFax());
		map.put("description", company.getDescription());
		map.put("logo", company.getId());
		map.put("title", "TRACK ASSET");
		
		byte[] data = JasperUtil.responseToByteArray(findAll(), "track-asset", map);
		
		return data;
	}

	@Override
	public ResDto sendEmailTrackAssetReport() throws Exception {
		byte[] data = pdfTrackAsset();
		
		EmailHelper emailHelper = new EmailHelper();
		emailHelper.setAttach(data);
		emailHelper.setFileName("track-asset.pdf");
		
		Users users = usersService.findByIdAuth();
		
		emailHandler.sendReport(users.getEmail(), "Track Asset Report", "Track Asset", emailHelper);
		
		ResDto resDto = new ResDto();
		resDto.setMsg("Send to Email");
		
		return resDto;
	}

}
