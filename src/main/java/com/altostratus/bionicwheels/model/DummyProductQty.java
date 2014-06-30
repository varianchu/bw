package com.altostratus.bionicwheels.model;

public class DummyProductQty {
	private Long id;
	private Double qty = 0.0;
	private Double price = 0.0;
	private Double cost = 0.0;
	private Double checkerQty = 0.0;
	private Double refundCost = 0.0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCheckerQty() {
		return checkerQty;
	}

	public void setCheckerQty(Double checkerQty) {
		this.checkerQty = checkerQty;
	}

	public Double getRefundCost() {
		return refundCost;
	}

	public void setRefundCost(Double refundCost) {
		this.refundCost = refundCost;
	}

}
