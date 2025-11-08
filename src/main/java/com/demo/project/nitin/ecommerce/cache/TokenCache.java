package com.demo.project.nitin.ecommerce.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenCache {

    @Cacheable(value = "blacklist", key = "#tokenId")
    public boolean isTokenBlackListed(String tokenId) {
        log.info("Checking if token {} is blacklisted...", tokenId);
        return false; // default → not blacklisted
    }

    @CachePut(value = "blacklist", key = "#tokenId")
    public boolean blackListToken(String tokenId) {
        log.info("Blacklisting token: {}", tokenId);
        return true; // cached as true
    }
}

