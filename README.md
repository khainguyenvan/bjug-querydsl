BJUG Querydsl 
===
This project demonstrates how you might use QueryDSL in a Spring project, with Spring Data, Spring MVC, JPA (Hibernate), JDBC and Mongodb.

JPA
-
The model `@Entities` are in src/main/java/net/eusashead/bjugquerydsl/data/entity.

The model is an extremely naive e-commerce system with an entity-attribute-value (EAV) style schema. It's not in any way an attempt to build a serious e-commerce model but it does demonstrate many of the features of QueryDSL.

There are some integration tests in src/test/java/net/eusashead/bjugquerydsl/data/entity/JpaQueryTest.java. These demonstrate the various query styles possible in QueryDSL, although they are intentionally simplistic to aid clarity.

The database is a H2 embedded database that is bootstrapped by the Spring container.

Spring Data JPA
-
There are Spring Data JPA `@Repositories` in `src/main/java/net/eusashead/bjugquerydsl/data/repository/` and very basic integration tests in `src/test/java/net/eusashead/bjugquerydsl/data/repository/`.

To generate the query types for JPA, the APT Maven plugin is used. This is linked to the process-resources goal so you can generate them using

`mvn proces-resources`

Spring Data Mongodb
-
Note: You need to have a local Mongodb instance running for these tests to run. There is no way of bootstrapping Mongodb in Spring (that I am aware of). The Mongo `@Document` class (Message) is in the JPA entity package and a `@Repository` is in the JPA repository package.

The query types are generated in the same way as for JPA above.

JDBC
-
To demonstrate the QueryDSL JDBC support and code generation requires several steps.

###Install database
You can install the H2 database to generate code against using the Maven sql plugin:

`mvn sql:execute`

This will create a local H2 database in a file in the root of the project.

###Generate query types and model classes

Then, to generate the query types

`mvn querydsl:export`

You can also generate the domain objects from the database using this task because the Maven plugin configuration contains `<exportBeans>true</exportBeans>`.

Spring MVC
-
There are a couple of Spring MVC controllers demonstrating how you might stitch together Spring Data and QueryDSL to create a REST API, using both standard JSON (`src/main/java/net/eusashead/bjugquerydsl/controller/ProductController.java`) and HAL (src/main/java/net/eusashead/bjugquerydsl/controller/SkuController.java).

You can run these in Jetty with `mvn jetty:run`

Accessing the standard JSON controller:
http://localhost:8080/app/product

Accessing the HAL controller:
http://localhost:8080/app/sku/?page=0&size=2&sort=skuId,desc

Notes for IntelliJ Users
-
To avoid needing to run maven commands outside of the IDE to generate the query types, you can integrate with the APT support in the IDE. This is available from IntelliJ Preferences (cmd + ,) - choose "Annotation Processors" in the "Compiler" settings, enable annotation processing and select to "Obtain processors from project class path".

Notes for Eclipse and Maven (m2e) users
-
If you use Eclipse and import the project as a Maven project, the APT plugins are run automatically. However, from time to time I found that this would not work and annotation processing would need to be triggered from the command using `mvn process-resources`.