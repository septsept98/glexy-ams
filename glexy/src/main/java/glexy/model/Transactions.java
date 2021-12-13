package glexy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.lawencon.base.BaseEntity;

@Entity
public class Transactions extends BaseEntity{
	
	private static final long serialVersionUID = -8411698396500371421L;

	@Column(name = "code_transaction", length = 20, nullable = false)
	private String codeTransaction;
	
	@Column(name = "checkout_date", nullable = false)
	private LocalDate checkOutDate;
	
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "employee_id", columnDefinition = "varchar")
	private Employee employeeId;
	
	@ManyToOne
	@JoinColumn(name = "location_id", columnDefinition = "varchar")
	private Location locationId;
	
	@ManyToOne
	@JoinColumn(name = "asset_general_id", columnDefinition = "varchar")
	private Asset assetGeneralId;

	public String getCodeTransaction() {
		return codeTransaction;
	}

	public void setCodeTransaction(String codeTransaction) {
		this.codeTransaction = codeTransaction;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Asset getAssetGeneralId() {
		return assetGeneralId;
	}

	public void setAssetGeneralId(Asset assetGeneralId) {
		this.assetGeneralId = assetGeneralId;
	}
	
}
