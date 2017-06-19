package com.example.demo.service;

import java.util.Date;

public class FileInformation extends FileMetaData {
	
	private static final long serialVersionUID = -8142132972473711061L;
	
	private byte[] fileData;
	    
	    public FileInformation( byte[] fileData, String fileName, Date fileCreatedDate, String uploadedBy) {
	        super(fileName, fileCreatedDate, uploadedBy);
	        this.setFileData(fileData);
	    }

		public FileInformation() {			
		}

		public byte[] getFileData() {
			return fileData;
		}

		public void setFileData(byte[] fileData) {
			this.fileData = fileData;
		}

}
