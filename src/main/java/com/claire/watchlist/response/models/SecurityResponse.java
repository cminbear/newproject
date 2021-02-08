package com.claire.watchlist.response.models;

public class SecurityResponse {
	
	public SecurityResponse() {
	}

	private String securityName;
	private String securitySymbol;
	private String securityIdentifier;
	private String description;
	private Boolean onWatchlist;
	private String lastPrice;
	private String openPrice; 
	private String closePrice;
	private String highPrice;
	private String lowPrice;
	private String volume;
	private String dateForLatestEOD;
	private MarketDataResponse defaultMarketData;
	
	public String getSecurityName() {
		return securityName;
	}
	
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	
	public String getSecuritySymbol() {
		return securitySymbol;
	}
	
	public void setSecuritySymbol(String securitySymbol) {
		this.securitySymbol = securitySymbol;
	}
	
	public String getSecurityIdentifier() {
		return securityIdentifier;
	}
	
	public void setSecurityIdentifier(String securityIdentifier) {
		this.securityIdentifier = securityIdentifier;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getOnWatchlist() {
		return onWatchlist;
	}
	
	public void setOnWatchlist(Boolean onWatchlist) {
		this.onWatchlist = onWatchlist;
	}

	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}

	public String getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(String closePrice) {
		this.closePrice = closePrice;
	}

	public String getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getDateForLatestEOD() {
		return dateForLatestEOD;
	}

	public void setDateForLatestEOD(String dateForLatestEOD) {
		this.dateForLatestEOD = dateForLatestEOD;
	}

	public MarketDataResponse getDefaultMarketData() {
		return defaultMarketData;
	}

	public void setDefaultMarketData(MarketDataResponse defaultMarketData) {
		this.defaultMarketData = defaultMarketData;
	}
	
}
