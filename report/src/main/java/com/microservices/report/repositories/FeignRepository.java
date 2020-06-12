package com.microservices.report.repositories;

import java.util.ArrayList;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.microservices.faculty.jpa.Course;

import feign.Headers;


@FeignClient(name="faculty")
@RibbonClient(name="faculty")
public interface FeignRepository {
	
	@GetMapping(value = "student/studentsForCoursePdf/{courseId}")
	public ArrayList<Object> getStudentsForCoursePdf(@PathVariable("courseId") Integer courseId ,@RequestHeader ("token") String token);
	
	@GetMapping(value = "course/one/{courseId}")
	public Course getOneCourse(@PathVariable("courseId") Integer courseId, @RequestHeader ("token") String token);

}
