package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;

@Entity
@Table(name = "customer")
public class Customer extends BaseObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "customer_name", nullable = false, length = 35)
	private String customerName;

	@Column(name = "contact_number", nullable = false)
	private String contactNumber;

	@Column(name = "city_location", nullable = false)
	private String cityLocation;

	@Column(name = "address", length = 45)
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "occupation", length = 20)
	private String occupation;

	@Column(name = "company", length = 20)
	private String company;

	@Column(name = "remarks", length = 50)
	private String remarks;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = { CascadeType.REMOVE })
	private List<Car> cars;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = { CascadeType.REMOVE })
	private List<AccountsReceivable> accountsReceivable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCityLocation() {
		return cityLocation;
	}

	public void setCityLocation(String cityLocation) {
		this.cityLocation = cityLocation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<AccountsReceivable> getAccountsReceivable() {
		return accountsReceivable;
	}

	public void setAccountsReceivable(
			List<AccountsReceivable> accountsReceivable) {
		this.accountsReceivable = accountsReceivable;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Customer != true) {
			return false;
		}
		final Customer o = (Customer) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return customerName;
	}
}
