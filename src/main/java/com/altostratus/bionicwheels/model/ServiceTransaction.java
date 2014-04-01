package com.altostratus.bionicwheels.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.altostratus.core.model.BaseObject;
import com.altostratus.core.model.User;

@Entity
@Table(name = "service_transaction")
public class ServiceTransaction extends BaseObject implements Serializable {

	@Transient
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Transient
	Date initDate = new Date();

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "service_date")
	private Date serviceDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceTransaction", cascade = CascadeType.REMOVE)
	private List<ServiceTransactionItem> serviceTransactionItems;

	@Column(name = "total_transaction_sale")
	private Double totalServiceTransactionSale;

	@Column(name = "total_transaction_profit")
	private Double totalServiceTransactionProfit;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "point_person_id")
	private User user;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "car")
	private String car;

	@Column(name = "mechanic_tireman")
	private String mechanicTireMan;

	@Transient
	private Long userId;

	@Transient
	private Long mechanicTireManId;

	@Transient
	private String startDate = df.format(initDate);

	@Transient
	private String endDate = df.format(initDate);

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getMechanicTireManId() {
		return mechanicTireManId;
	}

	public void setMechanicTireManId(Long mechanicTireManId) {
		this.mechanicTireManId = mechanicTireManId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public List<ServiceTransactionItem> getServiceTransactionItems() {
		return serviceTransactionItems;
	}

	public void setServiceTransactionItems(
			List<ServiceTransactionItem> serviceTransactionItems) {
		this.serviceTransactionItems = serviceTransactionItems;
	}

	public Double getTotalServiceTransactionSale() {
		return totalServiceTransactionSale;
	}

	public void setTotalServiceTransactionSale(
			Double totalServiceTransactionSale) {
		this.totalServiceTransactionSale = totalServiceTransactionSale;
	}

	public Double getTotalServiceTransactionProfit() {
		return totalServiceTransactionProfit;
	}

	public void setTotalServiceTransactionProfit(
			Double totalServiceTransactionProfit) {
		this.totalServiceTransactionProfit = totalServiceTransactionProfit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getMechanicTireMan() {
		return mechanicTireMan;
	}

	public void setMechanicTireMan(String mechanicTireMan) {
		this.mechanicTireMan = mechanicTireMan;
	}

	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof ServiceTransaction != true) {
			return false;
		}
		final ServiceTransaction o = (ServiceTransaction) other;
		return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	}

	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	public String toString() {
		return serviceDate.toString() + " - " + customerName + " - " + car;
	}

}
