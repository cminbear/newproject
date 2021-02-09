package com.claire.watchlist.marketstack.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketStackResponse {
	
	public MarketStackResponse() {
	}
	
	private List<DataResponse> data;
	
	public List<DataResponse> getData() {
		return data;
	}

	public void setData(List<DataResponse> data) {
		this.data = data;
	}
	
	
}
