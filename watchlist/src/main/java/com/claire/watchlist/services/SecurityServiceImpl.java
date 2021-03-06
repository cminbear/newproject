package com.claire.watchlist.services;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.claire.watchlist.constants.WatchlistConstants;
import com.claire.watchlist.domains.Security;
import com.claire.watchlist.marketstack.models.DataResponse;
import com.claire.watchlist.marketstack.models.MarketStackResponse;
import com.claire.watchlist.repositories.SecurityRepository;
import com.claire.watchlist.response.models.MarketDataResponse;
import com.claire.watchlist.response.models.SecurityResponse;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	@Autowired
	private SecurityRepository securityRepository;
	
	@Override
	public SecurityResponse saveOrUpdateSecurity(Security security) {
		
		SecurityResponse res = new SecurityResponse();
		
		Security savedSecurity = securityRepository.save(security);
		
		res.setSecurityName(savedSecurity.getSecurityName());
		res.setSecuritySymbol(savedSecurity.getSecuritySymbol());
		res.setSecurityIdentifier(savedSecurity.getSecurityIdentifier());
		res.setDescription(savedSecurity.getDescription());
		res.setOnWatchlist(savedSecurity.getOnWatchlist());
		
		return fetchSecurityMarketData(res, security.getSecuritySymbol());
	}
	
	@Override
	public List<SecurityResponse> restartDemoWatchList() {
		
		List<SecurityResponse> resList = new ArrayList<>();
		
		Iterable<Security> securities = securityRepository.findByOnWatchlist(false);
		
		for (Security security : securities) {
			security.setOnWatchlist(true);
		}
		
		securityRepository.saveAll(securities);
		Iterable<Security> allSecurities = securityRepository.findAll();
		
		for (Security security : allSecurities) {
			
			SecurityResponse res = new SecurityResponse();
			
			res.setSecurityName(security.getSecurityName());
			res.setSecuritySymbol(security.getSecuritySymbol());
			res.setSecurityIdentifier(security.getSecurityIdentifier());
			res.setDescription(security.getDescription());
			res.setOnWatchlist(security.getOnWatchlist());
			
			MarketDataResponse defaultMarketData = getMarketDataOneWeek(security.getSecurityIdentifier());
			defaultMarketData.setShowScale(false);
			res.setDefaultMarketData(defaultMarketData);

			resList.add(fetchSecurityMarketData(res, security.getSecuritySymbol()));
		}
		
		return resList;
	}
	
	@Override
	public SecurityResponse findSecurityByIndentifier(String id) {
		
		SecurityResponse res = new SecurityResponse();
		
		Security security = securityRepository.findBySecurityIdentifier(id);
		
		res.setSecurityName(security.getSecurityName());
		res.setSecuritySymbol(security.getSecuritySymbol());
		res.setSecurityIdentifier(security.getSecurityIdentifier());
		res.setDescription(security.getDescription());
		res.setOnWatchlist(security.getOnWatchlist());
		
		return fetchSecurityMarketData(res, security.getSecuritySymbol());
	}
	
	@Override
	public List<SecurityResponse> findSecuritiesOnWatchlist() {
		
		List<SecurityResponse> resList = new ArrayList<>();
		
		Iterable<Security> securities = securityRepository.findByOnWatchlist(true);
		
		for (Security security : securities) {
			
			SecurityResponse res = new SecurityResponse();
			
			res.setSecurityName(security.getSecurityName());
			res.setSecuritySymbol(security.getSecuritySymbol());
			res.setSecurityIdentifier(security.getSecurityIdentifier());
			res.setDescription(security.getDescription());
			res.setOnWatchlist(security.getOnWatchlist());
			
			MarketDataResponse defaultMarketData = getMarketDataOneWeek(security.getSecurityIdentifier());
			defaultMarketData.setShowScale(false);
			res.setDefaultMarketData(defaultMarketData);
			
			resList.add(fetchSecurityMarketData(res, security.getSecuritySymbol()));
		}
		
		return resList;
	}
	
	@Override
	public SecurityResponse updateSecurityOnWatchlist(String id) {

		SecurityResponse res = new SecurityResponse();
		
		Security security = securityRepository.findBySecurityIdentifier(id);
		security.setOnWatchlist(!security.getOnWatchlist());
		Security savedSecurity = securityRepository.save(security);
		
		res.setSecurityName(savedSecurity.getSecurityName());
		res.setSecuritySymbol(savedSecurity.getSecuritySymbol());
		res.setSecurityIdentifier(savedSecurity.getSecurityIdentifier());
		res.setDescription(savedSecurity.getDescription());
		res.setOnWatchlist(savedSecurity.getOnWatchlist());
		
		return fetchSecurityMarketData(res, security.getSecuritySymbol());	
	}
	
	@Override
	public MarketDataResponse getMarketDataOneWeek(String id) {
		
		CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		
		String symbol = securityRepository.findBySecurityIdentifier(id).getSecuritySymbol();
		String endpointForLatest = WatchlistConstants.LATEST_EOD_URL + symbol;
		
		MarketStackResponse marketStackResponse = restTemplate.getForObject(endpointForLatest, MarketStackResponse.class);
		String latestDate = marketStackResponse.getData().get(0).getDate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(WatchlistConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(WatchlistConstants.TIME_ZONE));
		Calendar cal  = Calendar.getInstance();
		try {
			cal.setTime(dateFormat.parse(latestDate));
		} catch (ParseException e) {
			log.error("Error occured while parsing date.", e);
		}
		cal.add(Calendar.DAY_OF_MONTH, -7);
		String weekBeforeLatestDate = dateFormat.format(cal.getTime());
		
		String endpointForOneWeek = WatchlistConstants.INTRADAY_URL + symbol + WatchlistConstants.URL_PARAM_DATE_FROM + weekBeforeLatestDate; 
		
		return fetchMarketDataByTimeRange(endpointForOneWeek, false, WatchlistConstants.TIME_RANGE_1W);
	}
	
	@Override
	public MarketDataResponse getMarketDataOneMonth(String id) {
		
		String symbol = securityRepository.findBySecurityIdentifier(id).getSecuritySymbol();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(WatchlistConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(WatchlistConstants.TIME_ZONE));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		String monthBeforeToday = dateFormat.format(cal.getTime());

		String endpoint = WatchlistConstants.EOD_URL + symbol + WatchlistConstants.URL_PARAM_DATE_FROM + monthBeforeToday; 
		
		return fetchMarketDataByTimeRange(endpoint, true, WatchlistConstants.TIME_RANGE_1M);
	}
	
	@Override
	public MarketDataResponse getMarketDataThreeMonths(String id) {
		
		String symbol = securityRepository.findBySecurityIdentifier(id).getSecuritySymbol();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(WatchlistConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(WatchlistConstants.TIME_ZONE));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -3);
		String threeMonthBeforeToday = dateFormat.format(cal.getTime());

		String endpoint = WatchlistConstants.EOD_URL + symbol + WatchlistConstants.URL_PARAM_DATE_FROM + threeMonthBeforeToday; 
		
		return fetchMarketDataByTimeRange(endpoint, true, WatchlistConstants.TIME_RANGE_3M); 
	}
	
	@Override
	public MarketDataResponse getMarketDataOneYear(String id) {
		
		String symbol = securityRepository.findBySecurityIdentifier(id).getSecuritySymbol();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(WatchlistConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(WatchlistConstants.TIME_ZONE));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		String yearBeforeToday = dateFormat.format(cal.getTime());
		
		String endpoint = WatchlistConstants.EOD_URL + symbol + WatchlistConstants.URL_PARAM_DATE_FROM + yearBeforeToday; 
		
		return fetchMarketDataByTimeRange(endpoint, true, WatchlistConstants.TIME_RANGE_1Y);
	}
	
	private MarketDataResponse fetchMarketDataByTimeRange(String endpoint, boolean isEOD, String timeRange) {
		
		MarketDataResponse res = new MarketDataResponse();
		RestTemplate restTemplate = new RestTemplate();
		
		MarketStackResponse marketStackResponse = restTemplate.getForObject(endpoint, MarketStackResponse.class);
		List<DataResponse> dataList = marketStackResponse.getData();
		List<BigDecimal> priceList = new ArrayList<>();
		List<String> label = new ArrayList<>();
		
		if (isEOD) {
			for (DataResponse data : dataList) {
				priceList.add(data.getAdj_close().setScale(2, BigDecimal.ROUND_HALF_UP));
				label.add("");
			}
		} else {
			for (DataResponse data : dataList) {
				priceList.add(data.getLast().setScale(2, BigDecimal.ROUND_HALF_UP));
				label.add("");
			}
		}

		res.setTimeRange(timeRange);
		res.setPriceList(priceList);
		res.setMinPrice(Collections.min(priceList));
		res.setMaxPrice(Collections.max(priceList));
		res.setLabel(label);
		
		return res;
	}
	
	private SecurityResponse fetchSecurityMarketData(SecurityResponse securityObj, String symbol) {

		CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		
		String latestEODEndpoint = WatchlistConstants.LATEST_EOD_URL + symbol; String
		latestIntradayEndpoint = WatchlistConstants.LATEST_INTRADAY_URL + symbol;
		 
		MarketStackResponse latesEODResponse = restTemplate.getForObject(latestEODEndpoint, MarketStackResponse.class);
		MarketStackResponse latesIntradayResponse = restTemplate.getForObject(latestIntradayEndpoint, MarketStackResponse.class);
		DataResponse latestEODData = latesEODResponse.getData().get(0); 
		DataResponse latestIntradayData = latesIntradayResponse.getData().get(0);
		
		NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
		
		securityObj.setOpenPrice(priceFormat.format(latestEODData.getAdj_open()));
		securityObj.setClosePrice(priceFormat.format(latestEODData.getAdj_close()));
		securityObj.setHighPrice(priceFormat.format(latestEODData.getAdj_high()));
		securityObj.setLowPrice(priceFormat.format(latestEODData.getAdj_low()));
		securityObj.setVolume(String.format("%,d", latestEODData.getAdj_volume()));
		securityObj.setLastPrice(priceFormat.format(latestIntradayData.getLast()));
		
		String latestEODDate = latestEODData.getDate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(WatchlistConstants.DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone(WatchlistConstants.TIME_ZONE));
		Calendar calForEOD = Calendar.getInstance(); 
		try {
			calForEOD.setTime(dateFormat.parse(latestEODDate)); }
		catch (ParseException e) { 
			log.error("Error occured while parsing date.", e); 
		}
		securityObj.setDateForLatestEOD(dateFormat.format(calForEOD.getTime()));
		
		return securityObj;
	}
}
