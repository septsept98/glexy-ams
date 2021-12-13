package glexy.model;

import javax.persistence.Entity;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "asset_types")
public class AssetType extends BaseEntity{

	private static final long serialVersionUID = 7115761567608885366L;

	private String name;
	
	private String code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
