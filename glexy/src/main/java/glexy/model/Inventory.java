package glexy.model;

public class Inventory {
	
	private String nameAsset;
	private String code;
	private int stock;
	private int latestStock;
	
	public String getNameAsset() {
		return nameAsset;
	}
	public void setNameAsset(String nameAsset) {
		this.nameAsset = nameAsset;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getLatestStock() {
		return latestStock;
	}
	public void setLatestStock(int latestStock) {
		this.latestStock = latestStock;
	}
	
	
}