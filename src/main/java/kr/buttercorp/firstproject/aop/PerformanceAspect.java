package kr.buttercorp.firstproject.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    // 특정 어노테이션 대상 지정
    @Pointcut("@annotation(kr.buttercorp.firstproject.annotation.RunningTime)")
    private void enableRunningTime() {
    }

    // 기본 패키지의 모든 메소드
    @Pointcut("execution(* kr.buttercorp.firstproject..*.*(..))")
    private void cut() {
    }

    @Around("cut() && enableRunningTime()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메소드 수행 전, 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 메소드 수행
        Object returningObj = joinPoint.proceed();


        // 메소드 수행 후, 측정 종료 및 로깅
        stopWatch.stop();
        String methodName = joinPoint.getSignature().getName();
        log.info("{}의 수행 시간: {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }
}
