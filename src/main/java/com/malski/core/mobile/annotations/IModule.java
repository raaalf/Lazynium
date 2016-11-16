package com.malski.core.mobile.annotations;

import org.openqa.selenium.support.FindBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface IModule {
    FindBy value() default @FindBy();
//    iOSFindBy value() default @iOSFindBy();
//    AndroidFindBy value() default @AndroidFindBy();
}
