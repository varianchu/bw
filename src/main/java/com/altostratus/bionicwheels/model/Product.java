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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "product")
public class Product extends BaseObject implements Serializable {

	public static enum UNIT_OF_MEASURE {
		BOX, PIECES, ROLL
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "product_name", nullable = false, length = 40)
	private String productName;

	@Column(name = "description", nullable = true, length = 50, columnDefinition = "TEXT")
	private String description;

	@Column(name = "total_quantity")
	private Double totalQty;

	@Column(name = "unit_of_measure")
	private String unitOfMeasure;
	// no cascade in original <-- okay
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	// no cascade in original <-- okay
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;

	// new add <-- need QA
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.PERSIST)
	private List<InventoryTransactionProduct> inventoryTransactionProducts;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<Inventory> inventories;

	@Column(name = "inventory_id")
	private Long inventoryId;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "thumbnail_url")
	private String thumbnailUrl;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public List<InventoryTransactionProduct> getInventoryTransactionProducts() {
		return inventoryTransactionProducts;
	}

	public void setInventoryTransactionProducts(
			List<InventoryTransactionProduct> inventoryTransactionProducts) {
		this.inventoryTransactionProducts = inventoryTransactionProducts;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Product != true) {
			return false;
		}
		final Product o = (Product) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return productName + " (" + code + ")";
	}
}
