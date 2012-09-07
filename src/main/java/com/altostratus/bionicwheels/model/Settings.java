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
@Table(name = "settings")
public class Settings extends BaseObject implements Serializable {

	public static enum STOCK_PROCESS {
		FIFO, LIFO
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "stock_process", nullable = false)
	private String stockProcess;

	@Column(name = "ceilingValue", nullable = false)
	private Double ceilingValue;

	@Column(name = "floorValue", nullable = false)
	private Double floorValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockProcess() {
		return stockProcess;
	}

	public void setStockProcess(String stockProcess) {
		this.stockProcess = stockProcess;
	}

	public Double getCeilingValue() {
		return ceilingValue;
	}

	public void setCeilingValue(Double ceilingValue) {
		this.ceilingValue = ceilingValue;
	}

	public Double getFloorValue() {
		return floorValue;
	}

	public void setFloorValue(Double floorValue) {
		this.floorValue = floorValue;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Settings != true) {
			return false;
		}
		final Settings o = (Settings) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return id.toString();
	}
}
