package ru.vluzhnykh.quickstart.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAOP {
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void methodInController() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void methodGetMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void methodPostMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void methodRequestMapping() {}

    // ....

    @Pointcut("methodInController() && (methodGetMapping() || methodPostMapping() || methodRequestMapping()))")
    public void methodsOfInterest(){}

    @AfterReturning(value = "methodsOfInterest()", returning = "result")
    public void logMethod(JoinPoint joinPoint, Greeting result) {
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Class[] paramTypes = ((CodeSignature) joinPoint.getSignature()).getParameterTypes();

        StringBuilder paramsValues = new StringBuilder();
        for (int i = 0; i < paramNames.length; i++) {
            paramsValues
                    .append(paramNames[i]).append(": ")
                    .append(paramTypes[i].getName()).append(" = ")
                    .append(joinPoint.getArgs()[i]);
            if (i < paramNames.length - 1) {
                paramsValues.append(", ");
            }
        }

        System.out.println(String.format("%s %s.%s(%s)",
                result,
                joinPoint.getSignature().getDeclaringType().getName(),
                joinPoint.getSignature().getName(), paramsValues));
    }

}
