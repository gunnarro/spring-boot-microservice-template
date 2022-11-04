# mymicroservice
Service description

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
	 
