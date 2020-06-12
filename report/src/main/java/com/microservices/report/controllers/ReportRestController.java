package com.microservices.report.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.faculty.jpa.*;
import com.microservices.faculty.service.FacultyService;
import com.microservices.report.repositories.FeignRepository;
import com.microservices.report.service.ReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Api(tags = { "Report operations" })
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ReportRestController {
	
	public final String REQUESTER = "report";

	@Autowired
	@Qualifier("ReportService")
	public ReportService reportService;

	@Autowired
	private FeignRepository feignRepository;

	@Autowired
	private ApplicationContext appContext;

	@ApiOperation("Returns students enrolled to a course with a id that is passed as a path variable (from ms faculty)")
	@GetMapping("/studentsForCourse/{courseId}")
	public ArrayList<Object> getstudentsForCourse(@PathVariable("courseId") Integer id,
			@RequestHeader("authToken") String token) throws Exception {

		ArrayList<Object> returnObject = feignRepository.getStudentsForCoursePdf(id, token);
		Boolean allowAccess = reportService.callLocalPepToGetDecision(token, REQUESTER , "read");
		if (!allowAccess) {
			throw new Exception("PEP FAILED");
		}
		return returnObject;
	}

	@ApiOperation("Returns pdf report of students enrolled to a course with a id that is passed as a path variable (from ms faculty)")
	@GetMapping("/studentsForCoursePdf/{courseId}")
	public void getstudentsForCoursePdf(@PathVariable("courseId") Integer id, @RequestHeader("authToken") String token,
		HttpServletResponse response) throws Exception {
		ArrayList<Object> students = feignRepository.getStudentsForCoursePdf(id, token);
		Course course = feignRepository.getOneCourse(id, token);

		Boolean allowAccess = reportService.callLocalPepToGetDecision(token, REQUESTER, "read");
		if (!allowAccess) {
			throw new Exception("PEP FAILED");
		}

		Resource resource = appContext.getResource("classpath:/reports/studentsForCourse.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);

		Map<String, Object> params = new HashMap<>();
		params.put("courseLabel", course.getLabel());
		params.put("courseName", course.getName());
		params.put("courseDate", course.getStartDate());
		params.put("teacherTitle", course.getTeacher().getTitle());
		params.put("teacherFirstName", course.getTeacher().getFirstName());
		params.put("teacherLastName", course.getTeacher().getLastName());

		BufferedImage img = null;
		if (course.getTeacher().getPicture() != null)
			img = ImageIO.read(new ByteArrayInputStream(course.getTeacher().getPicture()));
		params.put("teacherPicture", img);

		// PEP call

		generateReportPDF(response, jasperReport, dataSource, params);
	}

	private void generateReportPDF(HttpServletResponse resp, JasperReport jasperReport, JRDataSource dataSource,
			Map<String, Object> params) throws Exception {

		byte[] bytes = null;
		bytes = JasperRunManager.runReportToPdf(jasperReport, params, dataSource);
		resp.reset();
		resp.resetBuffer();
		resp.setContentType("application/pdf");
		resp.setContentLength(bytes.length);
		ServletOutputStream ouputStream = resp.getOutputStream();


		ouputStream.flush();
		ouputStream.close();
	}

}
