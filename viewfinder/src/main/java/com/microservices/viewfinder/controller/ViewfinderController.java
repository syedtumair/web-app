package com.microservices.viewfinder.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.viewfinder.service.ViewFinderService;
import com.microservices.viewfinder.controller.pojo.TokenDecisionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ViewfinderController {

	@Qualifier("ViewFinderService")
	public ViewFinderService viewFinderService;

	 @RequestMapping(
	    		path = "/getTokenDecision",
	    		method = RequestMethod.GET,
	    		produces = "application/json")
	public ResponseEntity<TokenDecisionResponse> getTokenDecision(@RequestHeader(value = "token", required = true) String token) {
		log.info("Decision making token recieved :  ... token[{}]", token);
		ResponseEntity<TokenDecisionResponse> response = viewFinderService.makeDecisionOnToken(token);
		return response;
	}

}
