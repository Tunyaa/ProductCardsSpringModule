package module.ProductCardsSpringModule.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 *
 * @author tunyaa
 */
@Aspect // Класс перехватвающий методы
@Component
public class ServiceMonitorAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Место, которое будет отслеживаться
    @Pointcut("execution(* module.ProductCardsSpringModule.service.*.*(..))")
    public void serviceMethods() {
    }

    // Выполняется ПЕРЕД методом
    @Before("serviceMethods()")
    public void logMethodEntry() {
        logger.info("🟢▷ Метод начал выполнение");
    }

    // Выполняется ПОСЛЕ метода (всегда)
    @After("serviceMethods()")
    public void logMethodExit() {
        logger.info("🛑 Сервисный метод завершил выполнение");
    }

    // Выполняется после УСПЕШНОГО завершения
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logMethodSuccess(Object result) {
        logger.info("✅ Метод успешно завершен. Результат: {}",
                result != null ? result.getClass().getSimpleName() : "null");
    }

    // Выполняется при ВОЗНИКНОВЕНИИ ИСКЛЮЧЕНИЯ
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logMethodException(Exception exception) {
        logger.error("❌ Ошибка в сервисном методе: {} - {}",
                exception.getClass().getSimpleName(), exception.getMessage());
    }

    // Включает всё вышеперечисленное
    @Around("serviceMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        // Информация о методе
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        StringBuilder argsInfo = new StringBuilder();

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                argsInfo.append(parameterNames[i] + "=" + args[i]);
            }
            logger.info("⚙ Аргументы метода {}.{} - {}", className, methodName, argsInfo);
        }

        logger.info("⏱️ Начало выполнения: {}.{}", className, methodName);

        // Замеряем время выполнения
        stopWatch.start();
        Object result = joinPoint.proceed(); // Выполняем целевой метод
        stopWatch.stop();

        // Логируем результат и время
        logger.info("⏱️ Завершение: {}.{} | Время выполнения: {} мс",
                className, methodName, stopWatch.getTotalTimeMillis());

        return result;
    }

    // Дополнительный Pointcut для методов с аннотацией @Monitor
    @Pointcut("@annotation(module.ProductCardsSpringModule.service.aop.Monitor)")
    public void monitoredMethods() {
    }

    // Только для методов с аннотацией @Monitor
    @Before("monitoredMethods()")
    public void logMonitoredMethod() {
        logger.info("💡 Мониторинг: выполняется аннотированный метод");
    }
}
