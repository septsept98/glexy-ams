package glexy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table
public class Users extends BaseEntity {
	
	private static final long serialVersionUID = -7120208257429487229L;

	@Column(length = 30, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String pass;
	
	@ManyToOne
	@JoinColumn(name = "users_img", columnDefinition = "varchar")
	private File usersImg;
	
	@ManyToOne
	@JoinColumn(name = "roles_id", columnDefinition = "varchar")
	private Roles rolesId;
	
	@OneToOne
	@JoinColumn(name = "employee_id", columnDefinition = "varchar")
	private Employee employeeId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public File getUsersImg() {
		return usersImg;
	}

	public void setUsersImg(File usersImg) {
		this.usersImg = usersImg;
	}

	public Roles getRolesId() {
		return rolesId;
	}

	public void setRolesId(Roles rolesId) {
		this.rolesId = rolesId;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
 