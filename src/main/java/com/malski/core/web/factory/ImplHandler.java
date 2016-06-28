package com.malski.core.web.factory;

public class ImplHandler {

    public static Class<?> getInterfaceImpl(Class interfaceType) throws ClassNotFoundException {
        String implName = interfaceType.getCanonicalName().replaceAll("\\.api\\.", "\\.impl\\.") + "Impl";
        return Class.forName(implName);
    }
}
