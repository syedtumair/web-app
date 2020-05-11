package com.microservices.viewfinder.service;

import org.springframework.http.ResponseEntity;

import com.microservices.viewfinder.controller.pojo.TokenDecisionResponse;

public interface ViewFinderService {
	public ResponseEntity<TokenDecisionResponse> makeDecisionOnToken(String token);
}
