package glexy.model;

import javax.persistence.Entity;

import com.lawencon.base.BaseEntity;

@Entity
public class Company extends BaseEntity{

	private static final long serialVersionUID = -7368437893815303686L;

	private String name;
	
	private String code;
	
	private String description;
	
	private String address;
	
	
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
