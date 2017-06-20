# SpringBootFileUploadApi
Sample Spring boot application with REST API for uploading,search and get specific file and scheduler to send email if new uploads are found

# Build and Run
Download the project to eclipse or cd SpringBootFileUploadApi
   
   # Update application.properties file under resources to appropriate propeties. These propeties are used in scheduler
      
```sh
mvn package
java -jar target/solution-0.1.0.jar
```
This is a spring boot application and will automatically launch embedded tomcat.

# API

API can be accessed at : localhost:8080/app/v1/file

* _Upload File_: /app/v1/file?file={file}&uploadedBy={uploadedBy}&date={date}

    * HTTP Method : POST
    * Request Params :
         1) file : Multipart file
         2) uploadedBy : name of the person uploading the file
         3) date : file creation date
    * Return type : File Meta Data
    
* _Get File_: /app/v1/file/{id}

    * HTTP Method : GET
    * Path Param : File Id
    * Return type : File stream
    
* _Search Files_ : /app/v1/file?uploadedBy={uploadedBy}&date={date}
    * HTTP Method : GET
    * Request Params :
        1) uploadedBy : name of the person uploading the file
        2) date : file creation date
    * Return type : List of file Meta Data
    
# Scheduler

Spring CRON scheduler is used to check updates made in last hour. application.properties file needs to be updated to connect to SMTP host to send mails.
