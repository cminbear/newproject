package com.claire.watchlist.response.models;

import java.math.BigDecimal;
import java.util.List;

public class MarketDataResponse {

	public MarketDataResponse() {
	}
	
	private String timeRange;
	private List<BigDecimal> priceList;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private List<String> label;
	private boolean showScale = true;
	
	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public List<BigDecimal> getPriceList() {
		return priceList;
	}
	
	public void setPriceList(List<BigDecimal> priceList) {
		this.priceList = priceList;
	}
	
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	public boolean isShowScale() {
		return showScale;
	}

	public void setShowScale(boolean showScale) {
		this.showScale = showScale;
	}
}
