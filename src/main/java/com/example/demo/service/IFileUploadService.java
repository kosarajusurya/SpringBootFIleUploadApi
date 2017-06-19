package com.example.demo.service;

import java.util.Date;
import java.util.List;

import com.example.demo.Exceptions.FileUploadException;

public interface IFileUploadService {
	
	FileMetaData save(FileInformation file) throws FileUploadException;

	List<FileMetaData> findFiles(String uploadedBy, Date date) throws FileUploadException;

	byte[] getFile(String id) throws FileUploadException;

}
