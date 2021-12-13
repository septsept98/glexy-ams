package glexy.dao;

import java.util.List;

import com.lawencon.base.BaseDaoImpl;

import glexy.model.TrackAsset;

public class TrackAssetDaoImpl extends BaseDaoImpl<TrackAsset> implements TrackAssetDao {

	@Override
	public List<TrackAsset> findAll() throws Exception {
		return getAll();
	}

	@Override
	public TrackAsset findById(String id) throws Exception {
		return getById(id);
	}

	@Override
	public TrackAsset saveOrUpdate(TrackAsset data) throws Exception {
		save(data);
		return null;
	}

}
