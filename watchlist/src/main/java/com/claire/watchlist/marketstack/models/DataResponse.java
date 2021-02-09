package com.claire.watchlist.marketstack.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataResponse {
	
	public DataResponse() {
	}
	
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal last;
	private BigDecimal close;
	private int volume;
	private BigDecimal adj_high;
	private BigDecimal adj_low;
	private BigDecimal adj_close;
	private BigDecimal adj_open;
	private int adj_volume;
	private String symbol;
	private String exchange;
	private String date;
	
	public BigDecimal getOpen() {
		return open;
	}
	
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	
	public BigDecimal getHigh() {
		return high;
	}
	
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	
	public BigDecimal getLow() {
		return low;
	}
	
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	
	public BigDecimal getLast() {
		return last;
	}
	
	public void setLast(BigDecimal last) {
		this.last = last;
	}
	
	public BigDecimal getClose() {
		return close;
	}
	
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public BigDecimal getAdj_high() {
		return adj_high;
	}
	
	public void setAdj_high(BigDecimal adj_high) {
		this.adj_high = adj_high;
	}
	
	public BigDecimal getAdj_low() {
		return adj_low;
	}
	
	public void setAdj_low(BigDecimal adj_low) {
		this.adj_low = adj_low;
	}
	
	public BigDecimal getAdj_close() {
		return adj_close;
	}
	
	public void setAdj_close(BigDecimal adj_close) {
		this.adj_close = adj_close;
	}
	
	public BigDecimal getAdj_open() {
		return adj_open;
	}
	
	public void setAdj_open(BigDecimal adj_open) {
		this.adj_open = adj_open;
	}
	
	public int getAdj_volume() {
		return adj_volume;
	}
	
	public void setAdj_volume(int adj_volume) {
		this.adj_volume = adj_volume;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getExchange() {
		return exchange;
	}
	
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
