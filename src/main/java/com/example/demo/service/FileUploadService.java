package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exceptions.FileUploadException;
import com.example.demo.data.IFileUploadData;

@Service("uploadService")
public class FileUploadService implements IFileUploadService{
	
	@Autowired
	IFileUploadData fileUploadData;

	@Override
	public FileMetaData save(FileInformation file) throws FileUploadException {		
		fileUploadData.insert(file);
		return new FileMetaData(file.getFileId(), file.getFileName(),  file.getFileCreatedDate(), file.getUploadedBy(), file.getUploadedDate());
	}

	@Override
	public List<FileMetaData> findFiles(String uploadedBy, Date date) throws FileUploadException {		
		return  fileUploadData.findByFileParams(uploadedBy, date);
	}

	@Override
	public byte[] getFile(String id) throws FileUploadException {
		FileInformation fileInfo = fileUploadData.getFileInfo(id);
		if(fileInfo != null){
			return fileInfo.getFileData();
		}
		return null;
	}

}
