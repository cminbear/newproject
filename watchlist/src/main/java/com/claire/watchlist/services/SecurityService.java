package com.claire.watchlist.services;

import java.util.List;

import com.claire.watchlist.domains.Security;
import com.claire.watchlist.response.models.MarketDataResponse;
import com.claire.watchlist.response.models.SecurityResponse;

public interface SecurityService {
	
	SecurityResponse saveOrUpdateSecurity(Security security);
	List<SecurityResponse> restartDemoWatchList();
	SecurityResponse findSecurityByIndentifier(String id);
	List<SecurityResponse> findSecuritiesOnWatchlist();
	SecurityResponse updateSecurityOnWatchlist(String id);
	MarketDataResponse getMarketDataOneWeek(String id);
	MarketDataResponse getMarketDataOneMonth(String id);
	MarketDataResponse getMarketDataThreeMonths(String id);
	MarketDataResponse getMarketDataOneYear(String id);
	
}
