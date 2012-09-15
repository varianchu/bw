package com.altostratus.bionicwheels.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.altostratus.core.model.User;

@Entity
@Table(name = "sales_person")
public class SalesPerson extends User {

	// @Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private Long id;
	//
	// @Column(name = "sales_lady_name", nullable = false)
	// private String salesLady;
	//
	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }
	//
	// public String getSalesLady() {
	// return salesLady;
	// }
	//
	// public void setSalesLady(String salesLady) {
	// this.salesLady = salesLady;
	// }
	//
	// public boolean equals(Object other) {
	// if (other == this) {
	// return true;
	// }
	// if (other instanceof SalesLady != true) {
	// return false;
	// }
	// final SalesLady o = (SalesLady) other;
	// return new EqualsBuilder().append(getId(), o.getId()).isEquals();
	// }
	//
	// public int hashCode() {
	// return (id != null ? id.hashCode() : 0);
	// }
	//
	// public String toString() {
	// return salesLady;
	// }
}
