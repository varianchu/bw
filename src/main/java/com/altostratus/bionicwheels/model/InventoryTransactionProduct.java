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

	@Column(name = "product_name", nullable = false)
	private String productName;

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
