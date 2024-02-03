package org.gunnarro.microservice.mymicroservice;

import org.gunnarro.microservice.mymicroservice.exception.ApplicationException;

import java.util.Arrays;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * Application start point.
 * run from cmd line:
 * mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=vktest,--logging.config=config/log4j2-spring.xml
 * or
 * java -Dspring.profiles.active=vktest -Dlogging.config=config/log4j2-spring.xml -jar target/spring-application.jar
 * <p>
 * Monitoring:
 * <a href="http://localhost:9998/actuator/prometheus">http://localhost:9998/actuator/prometheus</a>
 * <p>
 * Swagger url:
 * <a href="https://localhost:9999/swagger-ui/index.html">https://localhost:9999/swagger-ui/index.html</a>
 */
@SpringBootApplication
@ServletComponentScan
@EnableEncryptableProperties
@Slf4j
public class Application {
    private static final String DASHED_LINE = "-------------------------------------------------------------------------";

    public static void main(String[] args) {
        // do not allow use of command line arguments
        if (args != null && args.length > 0) {
            throw new ApplicationException(String.format("Error during startup. Do not use use java arguments! arguments=%s", Arrays.toString(args)));
        }

        ConfigurableApplicationContext ctx = null;
        try {
            SpringApplication springApplication = new SpringApplication(Application.class);
            // When spring boot application will start, It will write process id
            // to specified file at spring.pid.file, default application.pid
            // This pid file is used by the run.sh script in order stop and
            // display if application is running or not.
            // Do not allow java arguments (security issue)
            springApplication.addListeners(new ApplicationPidFileWriter());
            ctx = springApplication.run();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (ctx != null) {
                printApplicationInfo(ctx);
            }
        }
    }

    @PostConstruct
    public void start() {
        // no initialization needed for this application
    }

    @PreDestroy
    public void tearDown() {
        // no specific teardown needed for this application
        log.info(DASHED_LINE);
        log.info("Application Successfully Shutdown");
        log.info(DASHED_LINE);
    }

    private static void printApplicationInfo(ConfigurableApplicationContext ctx) {
        String profile = Arrays.toString(ctx.getEnvironment().getActiveProfiles());
        log.info("Active Profiles: {}", profile);
        log.info(DASHED_LINE);
        log.info("Application properties:");
        log.info(DASHED_LINE);
        log.info(DASHED_LINE);
        log.info("Server SSL properties:");
        log.info(DASHED_LINE);
        log.info("server.ssl.enabled          : {}", ctx.getEnvironment().getProperty("server.ssl.enabled"));
        log.info("server.ssl.protocol         : {}", ctx.getEnvironment().getProperty("server.ssl.enabled-protocols"));
        log.info("server.ssl.enabled-protocols: {}", ctx.getEnvironment().getProperty("server.ssl.enabled-protocols"));
        log.info("server.ssl.ciphers          : {}", ctx.getEnvironment().getProperty("server.ssl.ciphers"));
        log.info("server.ssl.key-store        : {}", ctx.getEnvironment().getProperty("server.ssl.key-store"));

        // doc:
        // https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
        log.info(DASHED_LINE);
        log.info("Server Properties:");
        log.info(DASHED_LINE);
        // server.connection-timeout= # Time that connectors wait for another
        // HTTP request before closing the connection.
        // When not set, the connector's container-specific default is used. Use
        // a value of -1 to indicate no (that is, an infinite) timeout.
        log.info("server.connection-timeout      : {}", ctx.getEnvironment().getProperty("server.connection-timeout"));
        log.info("server.session.timeout         : {}", ctx.getEnvironment().getProperty("server.session.timeout"));
        // server.tomcat.accept-count=0 # Maximum queue length for incoming
        // connection requests when all possible request processing threads are
        // in use.
        log.info("server.tomcat.accept-count     : {}", ctx.getEnvironment().getProperty("server.tomcat.accept-count"));
        log.info("server.tomcat.max-connections  : {}", ctx.getEnvironment().getProperty("server.tomcat.max-connections"));
        log.info("server.tomcat.max-threads, default 200 : {}", ctx.getEnvironment().getProperty("server.tomcat.max-threads"));
        log.info("server.tomcat.min-spare-threads: {}", ctx.getEnvironment().getProperty("server.tomcat.min-spare-threads"));

        log.info(DASHED_LINE);
        log.info("Application Successfully Started: {}", ctx.getApplicationName());
        log.info(DASHED_LINE);
    }
}
