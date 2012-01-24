package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name="category")
public class Category extends BaseObject implements Serializable
{

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="code", nullable=false, length = 20)
	private String code;

	@Column(name="description", nullable=true, length = 50)
	private String description;

	@Column(name="category_name", nullable=false, unique = true, length = 30)
	private String categoryName;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="category", cascade={CascadeType.REMOVE})
	private List<Product> products;

	public List<Product> getProducts() {
		return products;
	}


	public void setProducts(List<Product> products) {
		this.products = products;
	}


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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Category != true) {
			return false;
		}
		final Category o = (Category)other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}


	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return categoryName + " (" + code + ")";
	}
}
