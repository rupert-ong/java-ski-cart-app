# Simple Java Shopping Cart App

**Simple Java Shopping Cart App** is a persistent Java based application using a HttpServlets to handle and place order and JSTL for core, sql and formatting.

This project is written in [Java](https://www.oracle.com/java/) using a [PostgreSQL](https://www.postgresql.org/) database.

This strictly educational, proof of concept side project can be found in the Learning Java Web Development course at [O'Reilly](http://shop.oreilly.com/product/0636920048831.do), under "The Legendary Shopping Cart Example".

## Table of contents

* [Quick start](#quick-start)
* [What's included](#whats-included)
* [Contributors](#contributors)


## Quick start

Here's what you need to do to view this project:

1. Install [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html), the [Apache Tomcat](http://tomcat.apache.org/download-70.cgi) web server and the [Apache Ant](http://ant.apache.org/bindownload.cgi) build tool.
2. Install [PostgreSQL](https://www.postgresql.org/download/). Import the enclosed database (.sql file in the `data` folder) into a database called "skistuff". My database has a username of "rupert" and a password of "secret". If you change this, you will have to update the jsp/java files referencing the database.
3. Set up and start your Apache Tomcat server instance.
4. Within Windows Command Prompt, navigate to the root directory and run the command `ant deploy -Dwar.name=skicart`.
5. Within Windows Command Prompt, start the fakeSMTP server (for testing purposes) by navigating to the `src` folder and running `java -jar fakeSMTP-2.0.jar -s -b -p 2525`. Emails will appear in the `src/received-emails` folder.
6. Open your browser and navigate to `http://localhost:8080/skicart`. Your port number may be different depending on your server instance set up.


### What's included

Within the downloaded files, this is the relevant structure:

```
java-ski-cart-app/
├── build.xml
└── src
    └── data/
        ├── skiStuff.backup
        └── skiStuff.sql
    └── orders/
        ├── LineItem.java
        ├── Order.java
        ├── OrderHandler.java
        └── OrderPlacer.java
    ├── com.springsource.javax.servlet-2.5.0.jar (Not necessary if using Apache Tomcat)
    ├── jstl-api-1.2.jar
    ├── jstl-imp-1.2.jar
    ├── postgresql-jdbc.jar
    ├── web.xml
    └── *.jsp/html/css
```

## Contributors

**Rupert Ong**

* <https://twitter.com/rupertong>
* <https://github.com/rupert-ong>