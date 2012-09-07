package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.Date;

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

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "inventory")
public class Inventory extends BaseObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "quantity", nullable = false)
	private Double qty = 0.0;

	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "inventory_cost", nullable = false)
	private Double cost = 0.0;

	@Column(name = "suggest_price", nullable = false)
	private Double srp = 0.0;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Inventory != true) {
			return false;
		}
		final Inventory o = (Inventory) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return "inventory id: " + id.toString();
	}

}
