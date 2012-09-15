package com.altostratus.bionicwheels.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "tire")
public class ProductTire extends BaseObject implements Serializable {

	public static enum CONSTRUCTION {
		R, D, B, F
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "cross_section_width", nullable = false, length = 4)
	private String crossSectionWidth;

	@Column(name = "profile", nullable = false, length = 2)
	private Integer profile;

	@Column(name = "construction")
	private String construction;

	@Column(name = "diameter", nullable = false, length = 4)
	private String diameter;

	@OneToOne(cascade = { CascadeType.REMOVE })
	@JoinColumn(name = "product_id", nullable = false, unique = true)
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrossSectionWidth() {
		return crossSectionWidth;
	}

	public void setCrossSectionWidth(String crossSectionWidth) {
		this.crossSectionWidth = crossSectionWidth;
	}

	public Integer getProfile() {
		return profile;
	}

	public void setProfile(Integer profile) {
		this.profile = profile;
	}

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
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
		if (other instanceof ProductTire != true) {
			return false;
		}
		final ProductTire o = (ProductTire) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		if (construction == null) {
			return crossSectionWidth + "/" + profile.toString() + "/"
					+ diameter;
		} else {
			return crossSectionWidth + "/" + profile.toString() + "/"
					+ construction + "/" + diameter;
		}
	}
}
