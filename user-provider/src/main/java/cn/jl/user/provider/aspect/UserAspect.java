package cn.jl.user.provider.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("execution(public * cn.jl.user.provider.controller.UserController.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(){
        LOGGER.info("Before!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @After("log()")
    public void doAfter(){
        LOGGER.info("After!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
