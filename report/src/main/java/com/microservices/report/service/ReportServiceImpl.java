package com.microservices.report.service;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservices.faculty.service.FacultyService;
import com.microservices.report.utils.HttpUtil;
import com.squareup.okhttp.Response;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("ReportService")
public class ReportServiceImpl implements ReportService {
	private static final String APPLICATION_JSON = null;
	private static final String RESOURCE_NAME = "localPEPRequest.json";
	private static final String requesterReplacer = "{requester}";
	private static final String resourceReplacer = "{resource}";
	private static final String actionReplacer = "{action}";

	@Override
	public Boolean callLocalPepToGetDecision(String token, String requestingService, String action) throws Exception {
		String[] Segments = token.split(".v3.");
		String reformatedToken = Segments[0];
		log.info("**************** START ===>  LOCAL PEP Request ****************");
		log.info(" requester : {} " , reformatedToken);
	    log.info("requestingService : {} ", requestingService );
	    log.info("action : {}"  , action ) ;
		log.info("**************** END ===> Local PEP Request ****************");
		String requestBody = "";
		Response response;
		String strFullHost = "http://localhost:9500/invokePEP/report";
		HashMap<String, String> HeadersToHTTP = new HashMap<String, String>();
		HeadersToHTTP.put("token", reformatedToken);
		requestBody = requestMaker(RESOURCE_NAME, reformatedToken, requestingService, action);
		response = HttpUtil.doPost(APPLICATION_JSON, requestBody, strFullHost, HeadersToHTTP);
		if (response.isSuccessful()) {
			System.out.println(response);
			log.info("Response from LocalPEP (Policy Enforcement Point) : {} ", response.body().toString());
			if (response.code() == 200) {
				String responseBody = response.body().string();
				log.info("**************** START ===>  Response from PEP ****************");
				log.info("Response Body is {}", responseBody);
				log.info("**************** END ===> Response from PEP ****************");
				if (responseBody.contains("Permit")) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public String requestMaker(String bodyType, String requester, String resource, String action) {
		return FYPRequestMaker(RESOURCE_NAME).replace(requesterReplacer, requester)
				.replace(resourceReplacer, resource).replace(actionReplacer, action);
	}
}
