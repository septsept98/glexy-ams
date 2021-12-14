package com.lawencon.glexy.dto.asset;

import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.model.File;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.model.Invoice;

public class InsertReqDataAsset {
	
	private Asset asset;
	private Inventory inventory;
	private Invoice invoice;
	private File imgAsset;
	private File imgInvoice;
	
	
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
	public File getImgAsset() {
		return imgAsset;
	}
	public void setImgAsset(File imgAsset) {
		this.imgAsset = imgAsset;
	}
	public File getImgInvoice() {
		return imgInvoice;
	}
	public void setImgInvoice(File imgInvoice) {
		this.imgInvoice = imgInvoice;
	}
	
	
	
}
