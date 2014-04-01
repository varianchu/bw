package com.altostratus.bionicwheels.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "mechanicTireMan")
public class MechanicTireMan extends BaseObject implements Serializable {

	public static enum JOB_DESCRIPTION {
		TIREMAN, MECHANIC, TINT_INSTALLER, ELECTRICIAN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "mechanic_tireman_name", nullable = false)
	private String mechanicTireManName;

	@Column(name = "job_description")
	private String jobDescription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMechanicTireManName() {
		return mechanicTireManName;
	}

	public void setMechanicTireManName(String mechanicTireManName) {
		this.mechanicTireManName = mechanicTireManName;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof MechanicTireMan != true) {
			return false;
		}
		final MechanicTireMan o = (MechanicTireMan) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return mechanicTireManName;
	}
}
