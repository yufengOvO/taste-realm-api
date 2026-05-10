package edu.cdtu.annotation;

import java.lang.annotation.*;

//生成注解
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}
