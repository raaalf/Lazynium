package com.malski.core.web.annotations;

import org.openqa.selenium.support.How;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface PageInfo {
    String url() default "";
    How how() default How.UNSET;
    String title() default "";
    String using() default "";
    String equals() default "";
    String contains() default "";
}
