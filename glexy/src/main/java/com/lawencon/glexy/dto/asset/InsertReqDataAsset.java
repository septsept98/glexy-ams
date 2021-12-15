package com.lawencon.glexy.dto.asset;

import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;

public class InsertReqDataAsset {
	
	private Asset asset;
	private Inventory inventory;
	private Invoice invoice;
	
	
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
	
}
