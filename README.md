# SpringBootFileUploadApi
Sample Spring boot application with REST API for uploading,search and get specific file and scheduler to send email if new uploads are found

# Build and Run
Download the project to eclipse or cd SpringBootFileUploadApi
mvn package
java -jar target/solution-0.1.0.jar

This is a spring boot application and will automatically launch embedded tomcat.

# API

API can be accessed at : localhost:8080/app/v1/file

* _ Upload File : localhost:8080/app/v1/file?file={file}&uploadedBy={uploadedBy}&date={date} POST_
    * HTTP Method : POST
    * Request Params :
         1) file : Multipart file
         2) uploadedBy : String
         3) date : createdDate
  
    


