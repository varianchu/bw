package com.altostratus.bionicwheels.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;

@Entity
@Table(name = "inventory_transaction_product")
public class InventoryTransactionProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// original no cascading and join column <-- need QA for this
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "inventory_transaction_id", nullable = false)
	private InventoryTransaction inventoryTransaction;

	// original no cascading and join column <-- need QA for this
	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "product_id", nullable = true)
	private Product product;

	@Column(name = "quantity")
	private Double qty = 0.0;

	@Column(name = "unit_of_measure")
	private String uom;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "product_cost", nullable = false)
	private Double productCost;

	@Column(name = "product_sale", nullable = false)
	private Double productSale;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;

	@Transient
	private Long productId;

	@Transient
	private Long categoryId;

	@Transient
	private Long inventoryTransactionId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getInventoryTransactionId() {
		return inventoryTransactionId;
	}

	public void setInventoryTransactionId(Long inventoryTransactionId) {
		this.inventoryTransactionId = inventoryTransactionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InventoryTransaction getInventoryTransaction() {
		return inventoryTransaction;
	}

	public void setInventoryTransaction(
			InventoryTransaction inventoryTransaction) {
		this.inventoryTransaction = inventoryTransaction;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}

	public Double getProductSale() {
		return productSale;
	}

	public void setProductSale(Double productSale) {
		this.productSale = productSale;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof InventoryTransactionProduct != true) {
			return false;
		}
		final InventoryTransactionProduct o = (InventoryTransactionProduct) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return id.toString();
	}

}
