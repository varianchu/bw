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

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "car")
public class Car extends BaseObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "car_make", nullable = false)
	private String carMake;

	@Column(name = "car_model", nullable = false)
	private String carModel;

	@Column(name = "plate_number")
	private String plateNumber;

	@Column(name = "last_change_oil")
	private Date lastChangeOil;

	@Column(name = "next_change_oil")
	private Date nextChangeOil;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCarMake() {
		return carMake;
	}

	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Date getLastChangeOil() {
		return lastChangeOil;
	}

	public void setLastChangeOil(Date lastChangeOil) {
		this.lastChangeOil = lastChangeOil;
	}

	public Date getNextChangeOil() {
		return nextChangeOil;
	}

	public void setNextChangeOil(Date nextChangeOil) {
		this.nextChangeOil = nextChangeOil;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Car != true) {
			return false;
		}
		final Car o = (Car) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return carModel;
	}
}
