package ar.edu.unq.grupoh.criptop2p.webservice.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(0)
public class LogExecutionTimeAspectAnnotation {
    private Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspectAnnotation.class);

    @Around("@annotation(LogExecutionTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("/////// Log Execution Time");
        String signature = joinPoint.getSignature().toShortString();
        logger.info("/////// Method: " + signature);
        logger.info("/////// Args: " + Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info(String.format("/////// %1$s executed in %2$s ms", signature, executionTime));
        return  proceed;
    }
}
