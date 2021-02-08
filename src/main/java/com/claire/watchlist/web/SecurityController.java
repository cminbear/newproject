package com.claire.watchlist.web;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claire.watchlist.domains.Security;
import com.claire.watchlist.response.models.MarketDataResponse;
import com.claire.watchlist.response.models.SecurityResponse;
import com.claire.watchlist.services.SecurityService;

@RestController
@RequestMapping("/trade/security")
@CrossOrigin
public class SecurityController {

	@Autowired
	private SecurityService securityService;
	
	@PostMapping("")
	public ResponseEntity<SecurityResponse> createNewSecurity(@RequestBody Security security) throws ParseException {
		
		SecurityResponse res = securityService.saveOrUpdateSecurity(security);
		
		return new ResponseEntity<SecurityResponse>(res, HttpStatus.CREATED);
	}
	
	@GetMapping("/restart")
	public List<SecurityResponse> restartDemoWatchList() {
		
		List<SecurityResponse> resList = securityService.restartDemoWatchList();
		
		return resList;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SecurityResponse> getSecurityById(@PathVariable String id) {
		
		SecurityResponse res = securityService.findSecurityByIndentifier(id);
		
		return new ResponseEntity<SecurityResponse>(res, HttpStatus.OK) ;
	}
	
	@GetMapping("/watchlist") 
	public List<SecurityResponse> getSecuritiesOnWatchlist() {
		
		List<SecurityResponse> resList = securityService.findSecuritiesOnWatchlist();

		return resList;
	}
	
	@GetMapping("/updateOnWatchlist")
	public ResponseEntity<SecurityResponse> updateSecurityOnWatchlist(@RequestParam(value = "id") String id) {

		SecurityResponse res = securityService.updateSecurityOnWatchlist(id);

		return new ResponseEntity<SecurityResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/marketDataOneWeek")
	public ResponseEntity<MarketDataResponse> getMarketDataOneWeek(@RequestParam(value = "id") String id) {

		MarketDataResponse res = securityService.getMarketDataOneWeek(id);
		
		return new ResponseEntity<MarketDataResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/marketDataOneMonth")
	public ResponseEntity<MarketDataResponse> getMarketDataOneMonth(@RequestParam(value = "id") String id) {

		MarketDataResponse res = securityService.getMarketDataOneMonth(id);
		
		return new ResponseEntity<MarketDataResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/marketDataThreeMonths")
	public ResponseEntity<MarketDataResponse> getMarketDataThreeMonths(@RequestParam(value = "id") String id) {

		MarketDataResponse res = securityService.getMarketDataThreeMonths(id);
		
		return new ResponseEntity<MarketDataResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/marketDataOneYear")
	public ResponseEntity<MarketDataResponse> getMarketDataOneYear(@RequestParam(value = "id") String id) {

		MarketDataResponse res = securityService.getMarketDataOneYear(id);
		
		return new ResponseEntity<MarketDataResponse>(res, HttpStatus.OK);
	}
}
