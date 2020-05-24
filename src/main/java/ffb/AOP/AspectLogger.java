package ffb.AOP;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectLogger {
    private static final Logger logger = LogManager.getLogger(AspectLogger.class);

    @AfterReturning(pointcut = "execution(public String ffb.service.AlbumService.*(..))", returning = "result")
    public void logAlbumService(JoinPoint joinPoint, Object result) {
        logger.log(Level.INFO, "возвращенное значение: " + result.toString());
    }
    @AfterReturning(pointcut = "execution(public String ffb.service.SongService.*(..))", returning = "result")
    public void logSongService(JoinPoint joinPoint, Object result) {
        logger.log(Level.INFO, "возвращенное значение: " + result.toString());
    }
    @AfterReturning(pointcut = "execution(public String ffb.service.UserService.*(..))", returning = "result")
    public void logUserService(JoinPoint joinPoint, Object result) {
        logger.log(Level.INFO, "возвращенное значение: " + result.toString());
    }
}
