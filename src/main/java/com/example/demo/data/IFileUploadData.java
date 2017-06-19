package com.example.demo.data;

import java.util.Date;
import java.util.List;

import com.example.demo.Exceptions.FileUploadException;
import com.example.demo.service.FileInformation;
import com.example.demo.service.FileMetaData;

public interface IFileUploadData {
	
	 void insert(FileInformation fileInfo) throws FileUploadException;

	List<FileMetaData> findByFileParams(String uploadedBy, Date date) throws FileUploadException;

	FileInformation getFileInfo(String id) throws FileUploadException;
}
