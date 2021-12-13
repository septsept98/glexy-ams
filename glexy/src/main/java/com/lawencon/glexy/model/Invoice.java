package com.lawencon.glexy.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity{
	
	private static final long serialVersionUID = -2035680468034920096L;

	private String code;
	
	@Column(name = "purchase_date")
	private Date purchaseDate;
	
	@Column(name = "total_price")
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
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
