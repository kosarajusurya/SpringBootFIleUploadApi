package com.example.demo.scheduler.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.utils.FileUploadUtils;

@Service(value = "fileSystemUpdated")
public class FileSystemUpdated implements IFilesUpdated{

	@Override
	public boolean isNewFileAdded(Date date) {
		return FileUploadUtils.isFileSystemUpdated(date);
	}

}
