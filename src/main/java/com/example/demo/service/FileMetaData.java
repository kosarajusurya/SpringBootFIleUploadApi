package com.example.demo.service;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class FileMetaData  implements Serializable{

	private static final long serialVersionUID = -7298771703060272414L;
	
	private	 String fileId;
	private String fileName;
	private Date fileCreatedDate;
	private String uploadedBy;
    private Date uploadedDate;
    
    
    public FileMetaData(String fileName, Date fileCreatedDate, String uploadedBy) {
        this(UUID.randomUUID().toString(), fileName, fileCreatedDate, uploadedBy, new Date());
    }
   
	public FileMetaData(String fileId, String fileName, Date fileCreatedDate, String uploadedBy, Date uploadedDate) {       
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileCreatedDate = fileCreatedDate;
        this.uploadedBy = uploadedBy;
        this.uploadedDate = uploadedDate;
    }
    
	public FileMetaData() {

	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Date getFileCreatedDate() {
		return fileCreatedDate;
	}
	public void setFileCreatedDate(Date fileCreatedDate) {
		this.fileCreatedDate = fileCreatedDate;
	}

}
