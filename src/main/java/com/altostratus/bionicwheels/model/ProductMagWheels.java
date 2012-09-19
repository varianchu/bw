package com.altostratus.bionicwheels.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "magwheels")
public class ProductMagWheels extends BaseObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "style")
	private String style;

	@Column(name = "size", nullable = false, length = 5)
	private String size;

	@Column(name = "spokes")
	private Integer spokes;

	@Column(name = "offset", length = 3)
	private Integer offset;

	@Column(name = "pcd", nullable = false)
	private String pcd;

	@Column(name = "finish")
	private String finish;

	@OneToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "product_id", nullable = false, unique = true)
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getSpokes() {
		return spokes;
	}

	public void setSpokes(Integer spokes) {
		this.spokes = spokes;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getPcd() {
		return pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
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
		if (other instanceof ProductMagWheels != true) {
			return false;
		}
		final ProductMagWheels o = (ProductMagWheels) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return size + "-" + pcd;
	}

}
