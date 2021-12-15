package com.lawencon.glexy.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity{

	private static final long serialVersionUID = -5480037153902387528L;

	@Column(length = 20, nullable = false)
	private String code;
	
	@Column(name = "purchase_date", nullable = false)
	private LocalDate purchaseDate;
	
	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "invoice_img", columnDefinition = "varchar")
	private File invoiceImg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public File getInvoiceImg() {
		return invoiceImg;
	}

	public void setInvoiceImg(File invoiceImg) {
		this.invoiceImg = invoiceImg;
	}

	
}
