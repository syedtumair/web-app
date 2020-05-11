package com.microservices.viewfinder.controller.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder


public class TokenDecisionResponse {

    
    @JsonProperty("Access_Type")
    private String accessType;
    
    @JsonProperty("AccessLevel")
    private String accessLevel;
    
    @JsonProperty("ViewName")
    private String viewName;
    
    @JsonProperty("ViewId")
    private int viewId;
    
}
