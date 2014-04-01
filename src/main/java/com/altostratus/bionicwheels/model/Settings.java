package com.altostratus.bionicwheels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "settings")
public class Settings extends BaseObject implements Serializable {

	public static enum STOCK_PROCESS {
		FIFO, LIFO
	}

	public static enum FILTER_BY {
		CATEGORY, SUPPLIER, BRAND
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "stock_process", nullable = false)
	private String stockProcess;

	@Column(name = "filter_by", nullable = false)
	private String filterBy;

	@Column(name = "category_choice")
	private String categoryChoice;

	@Column(name = "brand_choice")
	private String brandChoice;

	@Column(name = "supplier_choice")
	private String supplierChoice;

	@Column(name = "ceilingValue", nullable = false)
	private Double ceilingValue;

	@Column(name = "floorValue", nullable = false)
	private Double floorValue;

	@Column(name = "transaction_number")
	private Integer transactionNumber = 1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockProcess() {
		return stockProcess;
	}

	public void setStockProcess(String stockProcess) {
		this.stockProcess = stockProcess;
	}

	public String getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}

	public Double getCeilingValue() {
		return ceilingValue;
	}

	public void setCeilingValue(Double ceilingValue) {
		this.ceilingValue = ceilingValue;
	}

	public Double getFloorValue() {
		return floorValue;
	}

	public void setFloorValue(Double floorValue) {
		this.floorValue = floorValue;
	}

	public String getCategoryChoice() {
		return categoryChoice;
	}

	public void setCategoryChoice(String categoryChoice) {
		this.categoryChoice = categoryChoice;
	}

	public String getBrandChoice() {
		return brandChoice;
	}

	public void setBrandChoice(String brandChoice) {
		this.brandChoice = brandChoice;
	}

	public String getSupplierChoice() {
		return supplierChoice;
	}

	public void setSupplierChoice(String supplierChoice) {
		this.supplierChoice = supplierChoice;
	}

	public Integer getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Settings != true) {
			return false;
		}
		final Settings o = (Settings) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return id.toString();
	}
}
