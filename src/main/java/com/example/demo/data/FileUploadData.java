package com.example.demo.data;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.FileUploadException;
import com.example.demo.service.FileInformation;
import com.example.demo.service.FileMetaData;
import com.example.demo.utils.FileUploadUtils;

@Service("fileUploadData")
public class FileUploadData implements IFileUploadData{
	
	private static final Logger log = Logger.getLogger(FileUploadData.class);
	
	 @PostConstruct
	 public void init() {
	    FileUploadUtils.createEmptyDirectory();
	 }			   

	@Override
	public void insert(FileInformation fileInfo) throws FileUploadException {
		 FileUploadUtils.createDirectory(fileInfo);		
		 try{
			 FileUploadUtils.saveFileData(fileInfo);
			 FileUploadUtils.saveMetaData(fileInfo); 
		 }catch(IOException ex){
			 log.error("Error occured while processing file with name :  " +  fileInfo.getFileName());
			 throw new FileUploadException("Error occured while uploading file as : "+ex.getMessage());
		 }				
	}

	@Override
	public List<FileMetaData> findByFileParams(String uploadedBy, Date date) throws FileUploadException {
		
		 try {
			return FileUploadUtils.findInFileSystem(uploadedBy,date);
		} catch (IOException | ParseException e) {
			 log.error("Error occured while getting file information");
			 throw new FileUploadException("Error occured while getting file information as : "+ e.getMessage());
		}
	}

	@Override
	public FileInformation getFileInfo(String id) throws FileUploadException {
		
		try {
			return FileUploadUtils.loadFromDirectory(id);
		} catch (IOException | ParseException e) {
			log.error("Error occured while getting file information for id : " + id);
			throw new FileUploadException("Error occured while getting file information as : "+ e.getMessage());
		}
	}

}
