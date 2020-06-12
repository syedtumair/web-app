package com.microservices.report.service;

import org.springframework.http.ResponseEntity;

import com.microservices.report.utils.FYPRequestMaker;

public interface ReportService extends FYPRequestMaker{
	
	public Boolean callLocalPepToGetDecision (String token , String requestingService , String action) throws Exception;

}
