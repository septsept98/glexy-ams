package glexy.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "transaction_detail")
public class TransactionDetail extends BaseEntity {

	private static final long serialVersionUID = -1764075380875113839L;

	@ManyToOne
	@JoinColumn(name = "transaction_id", columnDefinition = "varchar")
	private Transactions transactionId;
	
	private LocalDate durationDate;
	
	@ManyToOne
	@JoinColumn(name = "asset_id", columnDefinition = "varchar")
	private Asset assetId;
	
	@ManyToOne
	@JoinColumn(name = "status_asset_checkout_id", columnDefinition = "varchar")
	private StatusAsset statusAssetCheckoutId;
	
	private LocalDate dateCheckin;

	@ManyToOne
	@JoinColumn(name = "status_tr_checkin_id", columnDefinition = "varchar")
	private StatusTransaction statusTrCheckinId;

	public Transactions getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Transactions transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getDurationDate() {
		return durationDate;
	}

	public void setDurationDate(LocalDate durationDate) {
		this.durationDate = durationDate;
	}

	public Asset getAssetId() {
		return assetId;
	}

	public void setAssetId(Asset assetId) {
		this.assetId = assetId;
	}

	public StatusAsset getStatusAssetCheckoutId() {
		return statusAssetCheckoutId;
	}

	public void setStatusAssetCheckoutId(StatusAsset statusAssetCheckoutId) {
		this.statusAssetCheckoutId = statusAssetCheckoutId;
	}

	public LocalDate getDateCheckin() {
		return dateCheckin;
	}

	public void setDateCheckin(LocalDate dateCheckin) {
		this.dateCheckin = dateCheckin;
	}

	public StatusTransaction getStatusTrCheckinId() {
		return statusTrCheckinId;
	}

	public void setStatusTrCheckinId(StatusTransaction statusTrCheckinId) {
		this.statusTrCheckinId = statusTrCheckinId;
	}
}
