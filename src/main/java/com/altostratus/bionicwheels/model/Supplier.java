package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "supplier_name", nullable = false, unique = true)
	private String supplierName;

	@Column(name = "supplier_address", nullable = true, length = 50, columnDefinition = "TEXT")
	private String address;

	@Column(name = "contact_number", nullable = true)
	private Integer contactNumber;

	@Column(name = "notes", nullable = true, columnDefinition = "TEXT")
	private String notes;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.REMOVE)
	private List<Product> products;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.PERSIST)
	private List<Brand> brands;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Integer contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Supplier != true) {
			return false;
		}
		final Supplier o = (Supplier) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return supplierName;
	}

}
