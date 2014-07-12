package com.altostratus.bionicwheels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "service_rendered")
public class ServiceRendered extends BaseObject implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "service_made_part")
	private String serviceMadePart;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceMadePart() {
		return serviceMadePart;
	}

	public void setServiceMadePart(String serviceMadePart) {
		this.serviceMadePart = serviceMadePart;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof ServiceRendered != true) {
			return false;
		}
		final ServiceRendered o = (ServiceRendered) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return serviceMadePart.toString();
	}
}
