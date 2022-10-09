[![Microserice Template Known Vulnerabilities](https://snyk.io/test/github/gunnarro/spring-boot-microservice-template/badge.svg)](https://snyk.io/test/github/gunnarro/spring-boot-microservice-template)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gunnarro_spring-boot-microservice-template&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=gunnarro_spring-boot-microservice-template)

# mymicroservice
This is a generated spring boot microservice application based on the [spring boot microservice archetype](https://github.com/gunnarro/microservice-archetype)

Name | Firm | Mobile | Email 
--
developer | Company | myname@company.com | +47 00 00 00 00 |

 * Check out from git:
   * git clone git@github.com:<user>/mymicroservice.git
   
 * mvn eclispe:clean
 * mvn eclipse:eclipse
 * open eclipse and import the project
 
 * mvn clean install
 * mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=vktest,--spring.config.location=file:config/
   or 
   java -jar -Dspring.profiles.active=vktest -Dspring.config.location=file:config/ target/mymicroservice.jar
 
	[swagger api](https://localhost:xxxx/api-docs/swagger-ui.html)
	[Component documentation](https://github.com/gunnarro/microservice-archetype/wiki/documentation/mymicroservice)
	 
