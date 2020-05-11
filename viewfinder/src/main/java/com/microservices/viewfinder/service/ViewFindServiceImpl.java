package com.microservices.viewfinder.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.viewfinder.controller.pojo.TokenDecisionResponse;

import lombok.extern.slf4j.Slf4j;

@Service("ViewFinderService")
public class ViewFindServiceImpl implements ViewFinderService {

	@Override
	public ResponseEntity<TokenDecisionResponse> makeDecisionOnToken(String token) {	
		try {
			String[] Segments = token.split(".v3.");
			String DecisionSegment = Segments[0];
			if (DecisionSegment.equals("ROLE_USER"))
			{
				return new ResponseEntity<>(TokenDecisionResponse.builder().accessType("RoleUser").accessLevel("medium").viewName("RO").viewId(1).build(),
						HttpStatus.OK);
			} else if (DecisionSegment.equals("OAUTH_ADMIN")) {
				return new ResponseEntity<>(TokenDecisionResponse.builder().accessType("SuperUser").accessLevel("all").viewName("FullAdmin").viewId(2).build(),
						HttpStatus.OK);
			} else if (DecisionSegment.equals("TEACHER")) {
				return new ResponseEntity<>(TokenDecisionResponse.builder().accessType("LowUser").accessLevel("low").viewName("Teacher").viewId(3).build(),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>(TokenDecisionResponse.builder().accessType("-1").accessLevel("-1").viewName("Error").viewId(0).build(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>(TokenDecisionResponse.builder().accessType("-1").accessLevel("-1").viewName("Error").viewId(0).build(),
					HttpStatus.BAD_REQUEST);
		}
	}
}