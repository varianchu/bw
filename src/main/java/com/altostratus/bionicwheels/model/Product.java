package com.altostratus.bionicwheels.model;

import java.io.Serializable;

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
@Table(name="product")
public class Product extends BaseObject implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="code", nullable=false, unique=true)
	private String code;

	@Column(name ="product_name", nullable = false, length = 40)
	private String productName;

	@Column(name="description", nullable=true, length = 50, columnDefinition="TEXT")
	private String description;


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

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Product != true) {
			return false;
		}
		final Product o = (Product)other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}


	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return productName + " (" + code + ")";
	}
}
