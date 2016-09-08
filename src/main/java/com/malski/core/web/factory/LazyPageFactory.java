package com.malski.core.web.factory;

import com.malski.core.web.control.Browser;
import com.malski.core.web.control.LazySearchContext;
import com.malski.core.web.view.Page;
import com.malski.core.web.view.View;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class LazyPageFactory extends PageFactory {

    public static <T extends Page> T initElements(Browser browser, Class<T> pageClassToProxy) {
        T page = instantiatePage(browser, pageClassToProxy);
        initElements(browser, page);
        return page;
    }

    public static <T extends View> void initElements(Browser browser, T webComponent) {
        initElements(new LazyLocatorFactory(browser), webComponent);
    }

    public static <T extends View> T initElements(LazySearchContext searchContext, T webComponent) {
        initFields(new LazyFieldDecorator(new LazyLocatorFactory(searchContext)), webComponent);
        return webComponent;
    }

    public static <T extends View> void initElements(final ElementLocatorFactory factory, T webComponent) {
        initFields(new LazyFieldDecorator(factory), webComponent);
    }

    public static <T extends View> void initElements(FieldDecorator decorator, T webComponent) {
        PageFactory.initElements(decorator, webComponent);
    }

    public static void initFields(LazyFieldDecorator decorator, Object page) {
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
                } catch (IllegalAccessException var10) {
                    throw new RuntimeException(var10);
                }
            }
        }
    }

    private static <T extends Page> T instantiatePage(Browser browser, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(Browser.class);
                return constructor.newInstance(browser);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}