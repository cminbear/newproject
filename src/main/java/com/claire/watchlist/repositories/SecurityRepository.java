package com.claire.watchlist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.claire.watchlist.domains.Security;

@Repository
public interface SecurityRepository extends CrudRepository<Security, Long> {

	Security findBySecurityIdentifier(String securityId);	
	Iterable<Security> findByOnWatchlist(Boolean onWatchlist);
}
