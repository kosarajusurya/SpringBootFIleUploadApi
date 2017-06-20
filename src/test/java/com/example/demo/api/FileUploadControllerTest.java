package com.example.demo.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Exceptions.FileUploadException;
import com.example.demo.service.FileInformation;
import com.example.demo.service.FileMetaData;
import com.example.demo.service.IFileUploadService;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;


public class FileUploadControllerTest {
	
	private FileUploadController fileUploadController;
	private IFileUploadService fileUploadService;
	
	@Before
	public void setup() {
		// FileUploadController setup
		fileUploadController = new FileUploadController();
		fileUploadService = mock(IFileUploadService.class);
		fileUploadController.setUploadService(fileUploadService);		
	}
	
	@Test
	public void classMustHaveAnnotationRestControllerAndRequestMapping(){
		Boolean foundRestAnnotation = false;
		Boolean foundRequestMappingAnnotation = false;
		Annotation[] annotations = fileUploadController.getClass().getAnnotations();		
		for(Annotation annotation : annotations){
		    if(annotation instanceof RestController){
		    	foundRestAnnotation = true;		        
		    } 
		    if(annotation instanceof RequestMapping){
		    	foundRequestMappingAnnotation = true;
		    	RequestMapping mapping = (RequestMapping)annotation;
		    	assertTrue(mapping.value()[0].equals("/app/v1/file"));
		    }		   
		    
		}
		assertTrue(foundRestAnnotation);
		assertTrue(foundRequestMappingAnnotation);
	}

	@Test
	public void testHandleFileUploadMethodMustHavePOSTMethod() {
		try {			
			Method method = fileUploadController.getClass().getMethod("handleFileUpload", new Class[]{MultipartFile.class, String.class, Date.class});
			Annotation [] annotations = method.getAnnotations();
			assertTrue(annotations[0] instanceof RequestMapping);
			RequestMapping mapping = (RequestMapping)annotations[0];
			assertTrue(mapping.method()[0].equals(RequestMethod.POST));
		} catch (SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHandleFileUploadReturnsValidResponse() throws FileUploadException, IOException {
		byte[] fileBytes = new byte[10];		
		MultipartFile file = mock(MultipartFile.class);
		when(file.getBytes()).thenReturn(fileBytes);
		when(file.getOriginalFilename()).thenReturn("TEST");	
		FileMetaData metaData = mock(FileMetaData.class);
		when(fileUploadController.getUploadService().save(any(FileInformation.class))).thenReturn(metaData);
		ResponseEntity<FileMetaData> expectedResponse = new ResponseEntity<FileMetaData>(metaData, HttpStatus.OK);	
		assertEquals(fileUploadController.handleFileUpload(file, "TEST", new Date(123456789)), expectedResponse);
	}
	
	// Similar tests for SEARCH AND GET

	/*@Test
	public void testFindDocument() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDocument() {
		fail("Not yet implemented");
	}*/

}
