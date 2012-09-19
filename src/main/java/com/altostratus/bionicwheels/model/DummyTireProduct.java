package com.altostratus.bionicwheels.model;

public class DummyTireProduct {

	public static enum FILTERS {
		CrossSectionWidth_Profile_Diameter, Brand_Qty, Brand, Supplier, CrossSectionWidth, Profile, Diameter
	}

	private String filterName;
	private String crossSectionWidth;
	private String crossSectionWidth2;
	private Integer profile;
	private Integer profile2;
	private String diameter;
	private String diameter2;
	private Double qty;
	private String brandName;
	private String brandName2;
	private String supplierName;

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getCrossSectionWidth() {
		return crossSectionWidth;
	}

	public void setCrossSectionWidth(String crossSectionWidth) {
		this.crossSectionWidth = crossSectionWidth;
	}

	public Integer getProfile() {
		return profile;
	}

	public void setProfile(Integer profile) {
		this.profile = profile;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
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

	public String getCrossSectionWidth2() {
		return crossSectionWidth2;
	}

	public void setCrossSectionWidth2(String crossSectionWidth2) {
		this.crossSectionWidth2 = crossSectionWidth2;
	}

	public Integer getProfile2() {
		return profile2;
	}

	public void setProfile2(Integer profile2) {
		this.profile2 = profile2;
	}

	public String getDiameter2() {
		return diameter2;
	}

	public void setDiameter2(String diameter2) {
		this.diameter2 = diameter2;
	}

	public String getBrandName2() {
		return brandName2;
	}

	public void setBrandName2(String brandName2) {
		this.brandName2 = brandName2;
	}

}
