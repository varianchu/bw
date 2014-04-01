package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "brand")
public class Brand extends BaseObject implements Serializable {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "brand_name", nullable = false, unique = true)
	private String brandName;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;

	// remember to check the delete of brands for cascading in products - must
	// persist 3/14/14
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = CascadeType.PERSIST)
	private List<Product> products;

	@Column(name = "notes")
	private String notes;

	@Transient
	private Long supplierId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Brand != true) {
			return false;
		}
		final Brand o = (Brand) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return brandName;
	}

	public boolean isNew() {
		if (id == null)
			return true;
		else
			return false;
	}

}
