package com.malski.lazynium.web.factory;

import com.malski.lazynium.web.control.LazySearchContext;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;

public class LazyPageFactory extends PageFactory {

    public static void initElements(LazySearchContext searchContext) {
        initFields(new LazyFieldDecorator(new LazyLocatorFactory(searchContext)), searchContext);
    }

    private static void initFields(LazyFieldDecorator decorator, Object page) {
        for(Class proxyIn = page.getClass(); proxyIn != Object.class; proxyIn = proxyIn.getSuperclass()) {
            proxyFields(decorator, page, proxyIn);
        }
    }

    private static void proxyFields(LazyFieldDecorator decorator, Object page, Class<?> proxyIn) {
        for (Field field : proxyIn.getDeclaredFields()) {
            Object value = decorator.decorate(field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Exception during filed feeding", e);
                }
            }
        }
    }
}