package com.example.demo.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.INotificationService;

@Component
public class FileUploadScheduler {
	
	private static final Logger log = Logger.getLogger(FileUploadScheduler.class);
	
	@Autowired
	INotificationService emailNotificationService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "*/10 * * * * *" )
    public void reportCurrentTime() {
    	log.info("Invoking email notification service at time:  " + dateFormat.format(new Date()));
    	getNotificationService().notifyUsers();        
    }
    
    public INotificationService getNotificationService() {
        return emailNotificationService;
    }

    public void setNotificationService(INotificationService notificationService) {
        this.emailNotificationService = notificationService;
    }


}
