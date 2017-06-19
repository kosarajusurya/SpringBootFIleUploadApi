package com.example.demo.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.example.demo.service.FileInformation;
import com.example.demo.service.FileMetaData;

public class FileUploadUtils {

	public static final String DIRECTORY = "files";
	public static final String META_DATA_FILE_NAME = "metadata.properties";
	
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	 
	public static final String PROP_FILE_ID = "FILE_ID";
	public static final String PROP_FILE_NAME = "FILE_NAME";
	public static final String PROP_SUBMITTED_BY = "SUBMITTED_BY";
	public static final String PROP_SUBMITTED_DATE = "SUBMITTED_DATE";
	public static final String PROP_CREATED_DATE = "CREATED_DATE";

	public static String createDirectory(FileInformation fileInfo) {
		String path = getDirectoryPath(fileInfo);
		createDirectory(path);
		return path;
	}
	
	public static void saveFileData(FileInformation fileInfo) throws IOException {
		String path = getDirectoryPath(fileInfo);
		BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File(new File(path), fileInfo.getFileName())));
		stream.write(fileInfo.getFileData());
		stream.close();
	}

	public static void saveMetaData(FileInformation fileInfo) throws IOException {
		String path = getDirectoryPath(fileInfo);
		Properties props = new Properties();
		props.setProperty("FILE_ID", fileInfo.getFileId());
		props.setProperty("FILE_NAME", fileInfo.getFileName());
		props.setProperty("SUBMITTED_BY", fileInfo.getUploadedBy());		
		props.setProperty("SUBMITTED_DATE", DATE_FORMAT.format(fileInfo.getUploadedDate()));
		props.setProperty("CREATED_DATE", DATE_FORMAT.format(fileInfo.getFileCreatedDate()));
		File f = new File(new File(path), META_DATA_FILE_NAME);
		OutputStream out = new FileOutputStream(f);
		props.store(out, "File meta data");
	}

	public static void createEmptyDirectory() {
		createDirectory(DIRECTORY);
	}
	
	public static List<FileMetaData> findInFileSystem(String uploadedBy, Date date) throws IOException, ParseException {
		List<String> uuidList = getUuidList();
		List<FileMetaData> metadataList = new ArrayList<FileMetaData>(uuidList.size());
		for (String uuid : uuidList) {
			FileMetaData metadata = loadMetadataFromFileSystem(uuid);
			if (isMatched(metadata, uploadedBy, date)) {
				metadataList.add(metadata);
			}
		}
		return metadataList;
	}
	
	public static FileInformation loadFromDirectory(String id) throws IOException, ParseException {
		 FileMetaData metadata = loadMetadataFromFileSystem(id);
	       if(metadata==null) {
	           return null;
	       }
	       Path path = Paths.get(getFilePath(metadata));
	       FileInformation fileInfo = new FileInformation();
	       fileInfo.setFileData(Files.readAllBytes(path));
	       return fileInfo;
	}

	private static String getFilePath(FileMetaData metadata) {
		String dirPath = getDirectoryPath(metadata.getFileId());
        StringBuilder sb = new StringBuilder();
        sb.append(dirPath).append(File.separator).append(metadata.getFileName());
        return sb.toString();
	}

	private static String getDirectoryPath(FileInformation fileInfo) {
		return getDirectoryPath(fileInfo.getFileId());
	}

	private static String getDirectoryPath(String uuid) {
		StringBuilder sb = new StringBuilder();
		sb.append(DIRECTORY).append(File.separator).append(uuid);
		String path = sb.toString();
		return path;
	}

	private static void createDirectory(String path) {
		File file = new File(path);
		file.mkdirs();
	}
	
	private static List<String> getUuidList() {
		File file = new File(DIRECTORY);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		return Arrays.asList(directories);
	}

	private static boolean isMatched(FileMetaData metadata, String personName, Date date) {
		if (metadata == null) {
			return false;
		}
		boolean match = true;
		if (personName != null) {
			match = (personName.equals(metadata.getUploadedBy()));
		}
		if (match && date != null) {
			match = (date.equals(metadata.getFileCreatedDate()));
		}
		return match;
	}

	private static FileMetaData loadMetadataFromFileSystem(String uuid) throws IOException, ParseException {
		FileMetaData fileMetaData = null;
		String dirPath = getDirectoryPath(uuid);
		File file = new File(dirPath);
		if (file.exists()) {
			Properties properties = readProperties(uuid);
			fileMetaData = getFIleMetaDataFromProp(properties);

		}
		return fileMetaData;
	}

	private static FileMetaData getFIleMetaDataFromProp(Properties properties) throws ParseException {
		FileMetaData fileData = new FileMetaData();
		fileData.setFileId(properties.getProperty(PROP_FILE_ID));
		fileData.setFileName(properties.getProperty(PROP_FILE_NAME));
		fileData.setUploadedBy(properties.getProperty(PROP_SUBMITTED_BY));
		String dateStringCreated = properties.getProperty(PROP_CREATED_DATE);
		if (dateStringCreated != null) {
			fileData.setFileCreatedDate(DATE_FORMAT.parse(dateStringCreated));			
		}
		String dateStringUpdated = properties.getProperty(PROP_SUBMITTED_DATE);
		if (dateStringUpdated != null) {
			fileData.setUploadedDate(DATE_FORMAT.parse(dateStringUpdated));			
		}
		return fileData;
	}

	private static Properties readProperties(String uuid) throws IOException {
		Properties prop = new Properties();		
		try(FileInputStream input= new FileInputStream(new File(getDirectoryPath(uuid), META_DATA_FILE_NAME))) {			
			prop.load(input);
		} 
		return prop;
	}

	
}
