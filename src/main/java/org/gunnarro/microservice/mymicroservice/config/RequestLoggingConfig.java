package org.gunnarro.microservice.mymicroservice.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {
    /**
     * For request logging.
     * Must add org.springframework.web.filter.CommonsRequestLoggingFilter logger to log configuration
     */
    @Bean
    public CommonsRequestLoggingFilter logFilter(
            @Value("${request.log.filter.IncludeQueryString}") boolean includeQueryString,
            @Value("${request.log.filter.IncludePayload}") boolean includePayload,
            @Value("${request.log.filter.MaxPayloadLength}") int maxPayloadLength,
            @Value("${request.log.filter.IncludeHeaders}") boolean includeHeaders,
            @Value("${request.log.filter.IncludeClientInfo}") boolean includeClientInfo) {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(includeQueryString);
        filter.setIncludePayload(includePayload);
        filter.setMaxPayloadLength(maxPayloadLength);
        filter.setIncludeHeaders(includeHeaders);
        filter.setIncludeClientInfo(includeClientInfo);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }

    /**
     * For performance logging.
     * Must add org.gunnarro.microservice.mymicroservice.aspect logger to log configuration
     */
    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor() {
        CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
        String msg = String.format("class=%s method=%s (%s) responsetime=%s response=%s",
                CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME,
                CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME,
                CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS,
                CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME,
                CustomizableTraceInterceptor.PLACEHOLDER_RETURN_VALUE);
        cti.setExitMessage(msg);
        return cti;
    }

    /**
     * Aspect pointcut for performance logging
     */
    @Bean
    public Advisor endpointAdvisor(@Value("${performance.log.pointcut.expression}") String performanceLogPointcutExpression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(performanceLogPointcutExpression);
        return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
    }
}
