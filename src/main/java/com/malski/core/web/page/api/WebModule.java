package com.malski.core.web.page.api;

import com.malski.core.web.base.LazySearchContext;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.ElementWait;
import com.malski.core.web.factory.LazyLocator;

public interface WebModule extends WebView, ElementWait, LazySearchContext {
    Element getRoot();

    LazyLocator getLocator();
}
