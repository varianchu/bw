package com.altostratus.bionicwheels.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class DummyProduct {

	private Long id;
	private String code;
	private String productName;
	private String description;
	private String unitOfMeasure;
	private Long categoryId;
	private Long supplierId;
	private String brandName;
	private CommonsMultipartFile fileData;
	private Double totalQty = 0.0;
	private Double qty = 0.0;
	private Double cost = 0.0;
	private Double srp = 0.0;
	private Long inventoryId;
	private Long tireId;
	private Boolean newPrice;
	private String crossSectionWidth;
	private Integer profile;
	private String construction;
	private String diameter;

	private Long magsId;
	private String style;
	private String size;
	private Integer spokes;
	private Integer offset;
	private String pcd;
	private String finish;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getSrp() {
		return srp;
	}

	public void setSrp(Double srp) {
		this.srp = srp;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Boolean getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Boolean newPrice) {
		this.newPrice = newPrice;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
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

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public Long getTireId() {
		return tireId;
	}

	public void setTireId(Long tireId) {
		this.tireId = tireId;
	}

	public Long getMagsId() {
		return magsId;
	}

	public void setMagsId(Long magsId) {
		this.magsId = magsId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getSpokes() {
		return spokes;
	}

	public void setSpokes(Integer spokes) {
		this.spokes = spokes;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getPcd() {
		return pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

}
