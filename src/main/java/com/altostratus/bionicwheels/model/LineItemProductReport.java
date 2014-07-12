package com.altostratus.bionicwheels.model;

public class LineItemProductReport {

	private Product product;
	private Double costOfGood = 0.0;
	private Double totalCostOfGoodSold = 0.0;
	private Double totalSRP = 0.0;
	private Double totalProfit = 0.0;
	private Double totalPurchaseCost = 0.0;
	private Double netProfit = 0.0;
	private Double qty = 0.0;
	private Double qtyIn = 0.0;
	private Double qtyOut = 0.0;
	private String serviceMadePart = "";

	public Double getTotalCostOfGoodSold() {
		return totalCostOfGoodSold;
	}

	public void setTotalCostOfGoodSold(Double totalCostOfGoodSold) {
		this.totalCostOfGoodSold = totalCostOfGoodSold;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getTotalSRP() {
		return totalSRP;
	}

	public void setTotalSRP(Double totalSRP) {
		this.totalSRP = totalSRP;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Double getTotalPurchaseCost() {
		return totalPurchaseCost;
	}

	public void setTotalPurchaseCost(Double totalPurchaseCost) {
		this.totalPurchaseCost = totalPurchaseCost;
	}

	public Double getCostOfGood() {
		return costOfGood;
	}

	public void setCostOfGood(Double costOfGood) {
		this.costOfGood = costOfGood;
	}

	public Double getQtyIn() {
		return qtyIn;
	}

	public void setQtyIn(Double qtyIn) {
		this.qtyIn = qtyIn;
	}

	public Double getQtyOut() {
		return qtyOut;
	}

	public void setQtyOut(Double qtyOut) {
		this.qtyOut = qtyOut;
	}

	public Double getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(Double netProfit) {
		this.netProfit = netProfit;
	}

	public String getServiceMadePart() {
		return serviceMadePart;
	}

	public void setServiceMadePart(String serviceMadePart) {
		this.serviceMadePart = serviceMadePart;
	}

}
