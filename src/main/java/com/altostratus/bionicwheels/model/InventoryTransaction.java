package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction extends BaseObject implements Serializable {

	public static enum TRANSACTION_TYPE {
		INVENTORY_IN, INVENTORY_OUT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "date_created", nullable = false)
	private Date dateCreated = new Date();

	@Column(name = "transaction_type", nullable = false)
	private String transactionType;

	@Column(name = "referenceNumber", nullable = false, unique = true)
	private String referenceNumber;

	// original - no one to many needs QA
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryTransaction", cascade = CascadeType.REMOVE)
	private List<InventoryTransactionProduct> inventoryTransactionProducts;

	@Transient
	private String dateCreatedValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getDateCreatedValue() {
		return dateCreatedValue;
	}

	public void setDateCreatedValue(String dateCreatedValue) {
		this.dateCreatedValue = dateCreatedValue;
	}

	public List<InventoryTransactionProduct> getInventoryTransactionProducts() {
		return inventoryTransactionProducts;
	}

	public void setInventoryTransactionProducts(
			List<InventoryTransactionProduct> inventoryTransactionProducts) {
		this.inventoryTransactionProducts = inventoryTransactionProducts;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof InventoryTransaction != true) {
			return false;
		}
		final InventoryTransaction o = (InventoryTransaction) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return "reference id: " + id.toString();
	}
}
