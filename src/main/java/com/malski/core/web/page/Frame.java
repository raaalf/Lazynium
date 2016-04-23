package com.malski.core.web.page;

public interface Frame extends WebModule {

    default Frame performAction(Runnable function) {
        getBrowser().switchTo().frame(getRoot());
        function.run();
        getBrowser().switchTo().defaultContent();
        return this;
    }
}