package glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "status_assets")
public class StatusAsset extends BaseEntity {

	private static final long serialVersionUID = -8607376696599223467L;

	@Column(name = "code_status_asset", length = 20, nullable = false)
	private String codeStatusAsset;
	
	@Column(name = "name_status_asset", length = 30, nullable = false)
	private String nameStatusAsset;

	public String getCodeStatusAsset() {
		return codeStatusAsset;
	}

	public void setCodeStatusAsset(String codeStatusAsset) {
		this.codeStatusAsset = codeStatusAsset;
	}

	public String getNameStatusAsset() {
		return nameStatusAsset;
	}

	public void setNameStatusAsset(String nameStatusAsset) {
		this.nameStatusAsset = nameStatusAsset;
	}
	
	

}
