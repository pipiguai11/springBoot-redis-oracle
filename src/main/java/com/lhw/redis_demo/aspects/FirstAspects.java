package com.lhw.redis_demo.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class FirstAspects {

    /**
     * execution
     *
     * 第一个"*"表示通配任意类型的返回值
     * 第一个".."表示包含这个包以及其子包
     * 第二个"*"表示包中的所有类
     * 第三个"*"表示所有类中的所有方法
     * （..）表示所有方法的任意参数
     *
     * 这个表达式意思是匹配services包下的所有的类的所有方法
     *
     */
    @Pointcut("execution(* com.lhw.redis_demo.services..*.*(..))")
    public void execut(){}

    @Pointcut("within(com.lhw.redis_demo.services.*)")
    public void with(){}

    @Pointcut("this(com.lhw.redis_demo.services.TestService)")
    public void thisTest(){}

    @Pointcut("bean(testService)")
    public void beanTest(){}

//    @Before("execut()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("------------------doBefore------------------------");
        log.info("切面-前置增强。。。。。");
        System.out.println("|  所有入参  |" +Arrays.toString(joinPoint.getArgs()));
        //返回一个表示连接点类型的字符串，比如我这个增强连接点用的是Execution，得到的结果就为（method-execution）
        System.out.println(joinPoint.getKind());
        //我们可以在这个署名对象中获取到目标方法名，目标类名等信息
        System.out.println("|  署名对象  |" +joinPoint.getSignature());
        //返回与连接点对应的源位置。（org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint$SourceLocationImpl@605c62c7）
        System.out.println(joinPoint.getSourceLocation());
        //返回一个对象，该对象封装此连接点的静态部分。（execution(List com.lhw.redis_demo.services.TestService.getAll())）
        System.out.println(joinPoint.getStaticPart());
        System.out.println("| 被代理对象 |" +joinPoint.getTarget());
        System.out.println("|  代理对象  |" + joinPoint.getThis());
        System.out.println("---------------------------------------------------");
    }

//    @After("with()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("------------------doAfter------------------------");
        log.info("切面-后置增强");
        //对joinPoint的操作这里就不写了，看doBefore就好了
        System.out.println("-------------------------------------------------");
    }

//    @AfterReturning(pointcut = "thisTest()",returning = "returnVal")
    public void doAfterRerurning(Joinpoint joinpoint,Object returnVal){
        System.out.println("------------------doAfterRerurning------------------------");
        log.info("切面-后置增强");
        //对joinPoint的操作这里就不写了，看doBefore就好了
        System.out.println(returnVal);
        System.out.println("----------------------------------------------------------");
    }

//    @AfterThrowing(pointcut = "thisTest()",throwing = "error")
    public void doAfterThrowing(Joinpoint joinpoint,Throwable error){
        System.out.println("------------------doAfterThrowing------------------------");
        log.info("切面-后置增强");
        //对joinPoint的操作这里就不写了，看doBefore就好了
        System.out.println(error);
        System.out.println("----------------------------------------------------------");
    }

    /**
     * 当使用@Around做环绕通知时，有两点必须要注意
     *      1、形参必须时用ProceedingJoinPoint对象，不能用JoinPoint，
     *          虽然说ProceedingJoinPoint继承了JoinPoint
     *          但是我们需要调用ProceedingJoinPoint中的proceed方法做代理调用
     *          在调用proceed之前和之后，可以做我们需要的操作
     *      2、如果执行的代理方法有返回值，那这里就必须有返回值来代替代理方法返回结果，
     *          这里一般用Object代替所有类型的返回值
     *          如果不写返回值，那很容易出问题，调用有返回值的方法时会结果丢失
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
//    @Around("beanTest()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("------------------doAround------------------------");
        log.info("切面-环绕增强-执行前");
        Object result = joinPoint.proceed();
        log.info("切面-环绕增强-执行后");
        System.out.println("----------------------------------------------------------");
        return result;
    }

}
