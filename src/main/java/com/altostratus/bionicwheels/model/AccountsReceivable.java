package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.Date;

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

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "accounts_receivable")
public class AccountsReceivable extends BaseObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "receipt_reference_number", nullable = false, unique = true)
	private String receiptNumber;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "date_created", nullable = false)
	private Date dateCreated;

	@Column(name = "date_to_receive", nullable = false)
	private Date expectedDateReceivable;

	@Transient
	private String dateCreatedValue;

	@Transient
	private String expectedDateReceivableValue;

	@Transient
	private Long customerId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getExpectedDateReceivable() {
		return expectedDateReceivable;
	}

	public void setExpectedDateReceivable(Date expectedDateReceivable) {
		this.expectedDateReceivable = expectedDateReceivable;
	}

	public String getDateCreatedValue() {
		return dateCreatedValue;
	}

	public void setDateCreatedValue(String dateCreatedValue) {
		this.dateCreatedValue = dateCreatedValue;
	}

	public String getExpectedDateReceivableValue() {
		return expectedDateReceivableValue;
	}

	public void setExpectedDateReceivableValue(
			String expectedDateReceivableValue) {
		this.expectedDateReceivableValue = expectedDateReceivableValue;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		if (other == this) {
			return true;
		}
		if (other instanceof AccountsReceivable != true) {
			return false;
		}
		final AccountsReceivable o = (AccountsReceivable) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (id != null ? id.hashCode() : 0);
	}

	public boolean isNew() {
		if (id == null)
			return true;
		else
			return false;
	}
}
