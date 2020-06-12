package com.microservices.report.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public interface FYPRequestMaker {
	
	public default String FYPRequestMaker(String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        return new BufferedReader(new InputStreamReader(inStream)).lines()
                .collect(Collectors.joining("\n"));
    }

}
