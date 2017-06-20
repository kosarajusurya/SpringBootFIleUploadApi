package com.example.demo.api;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Exceptions.FileUploadException;
import com.example.demo.service.FileInformation;
import com.example.demo.service.FileMetaData;
import com.example.demo.service.IFileUploadService;

@RestController
@RequestMapping("/app/v1/file")
public class FileUploadController {

	private static final Logger log = Logger.getLogger(FileUploadController.class);

	@Autowired
	IFileUploadService uploadService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<FileMetaData> handleFileUpload(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "uploadedBy", required = true) String uploadedBy,
			@RequestParam(value = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)
			throws FileUploadException, IOException {
		FileMetaData fileData = getUploadService()
				.save(new FileInformation(file.getBytes(), file.getOriginalFilename(), date, uploadedBy));
		log.debug("Request to save file with name : "+ file.getOriginalFilename() +" uploaded by : "+ uploadedBy);
		return new ResponseEntity<FileMetaData>(fileData, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FileMetaData>> findDocument(
			@RequestParam(value = "uploadedBy", required = false) String uploadedBy,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws FileUploadException {		
		return new ResponseEntity<List<FileMetaData>>(getUploadService().findFiles(uploadedBy, date), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getDocument(@PathVariable String id) throws FileUploadException {		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(getUploadService().getFile(id), httpHeaders, HttpStatus.OK);
	}
	
	public IFileUploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(IFileUploadService uploadService) {
		this.uploadService = uploadService;
	}
}
