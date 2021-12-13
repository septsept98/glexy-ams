package glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table
public class Permissions extends BaseEntity {

	private static final long serialVersionUID = -2221432347755352384L;

	@Column(name ="name_permission",length = 30, nullable = false)
	private String namePermission;
	
	@Column(length = 20, nullable = false)
	private String code;

	public String getNamePermission() {
		return namePermission;
	}

	public void setNamePermission(String namePermission) {
		this.namePermission = namePermission;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
