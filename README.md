# RMS
Study case project using various technology stack for building Resource Management System

## Overview
The application is using maven multimodule where each module will provides the same functionalities with different technology stack.

```
rms (parent project)
|-- src
|   |-- main
|       |-- java (java source file)
|       |-- resources (configuration, properties)
|       |-- webapp (web specific files, css, js, jsp, html)
|   |-- test
|       |-- java (java test source file)
|-- pom.xml
|-- rms.sql (sample PostgreSQL data)
```

## rms
It is implementing MVC pattern using only Servlet and JSP, combine with plain JDBC to handle database operation.

It uses tomcat7-maven-plugin to spin up embedded tomcat 7, therefore no need to install tomcat 7 on your local machine. 

To run the application, execute maven command `mvn tomcat7:run` and browse http://localhost:8080/rms
