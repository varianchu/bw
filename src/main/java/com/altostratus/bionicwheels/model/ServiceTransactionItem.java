package com.altostratus.bionicwheels.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "service_transaction_item")
public class ServiceTransactionItem extends BaseObject implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "service_transaction_id", nullable = false)
	private ServiceTransaction serviceTransaction;

	@Column(name = "service_made_part")
	private String serviceMadePart;

	@Column(name = "servicePrice")
	private Double servicePrice;

	@Column(name = "serviceProfit")
	private Double serviceProfit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceTransaction getServiceTransaction() {
		return serviceTransaction;
	}

	public void setServiceTransaction(ServiceTransaction serviceTransaction) {
		this.serviceTransaction = serviceTransaction;
	}

	public String getServiceMadePart() {
		return serviceMadePart;
	}

	public void setServiceMadePart(String serviceMadePart) {
		this.serviceMadePart = serviceMadePart;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public Double getServiceProfit() {
		return serviceProfit;
	}

	public void setServiceProfit(Double serviceProfit) {
		this.serviceProfit = serviceProfit;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof ServiceTransactionItem != true) {
			return false;
		}
		final ServiceTransactionItem o = (ServiceTransactionItem) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return serviceMadePart;
	}

}
