package com.example.my_annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 运行时注解
 */
@Documented
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME) //运行时注解
public  @interface  MyRunTimeAnnotation {
    public int id() default 0;
    public String name() default "";
    public int age() default 0;
}


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@interface ClassRuntimeAnnotation{
    int id() default 0;
}