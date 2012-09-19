package com.altostratus.bionicwheels.model;

public class DummyMagsProduct {

	public static enum FILTERS {
		SIZE_PCD, SIZE, PCD, BRAND, SUPPLIER
	}

	private String filterName;
	private String size;
	private String size2;
	private String pcd;
	private String pcd2;
	private String brandName;
	private String supplierName;

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSize2() {
		return size2;
	}

	public void setSize2(String size2) {
		this.size2 = size2;
	}

	public String getPcd() {
		return pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	public String getPcd2() {
		return pcd2;
	}

	public void setPcd2(String pcd2) {
		this.pcd2 = pcd2;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
