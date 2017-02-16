package com.malski.core.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface FindBy {

    String id() default "";

    String name() default "";

    String className() default "";

    String css() default "";

    String tagName() default "";

    String linkText() default "";

    String partialLinkText() default "";

    String xpath() default "";

    String model() default "";

    String binding() default "";

    String buttonText() default "";

    String cssContainingText() default "";

    String deepCss() default "";

    String exactBinding() default "";

    String options() default "";

    String partialButtonText() default "";

    String repeater() default "";

    String repeaterCell() default "";

    String repeaterColumn() default "";

    String repeaterRow() default "";
}
