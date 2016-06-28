package com.malski.core.web.page.api;

import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.ElementWait;

public interface WebModule extends WebView, ElementWait {
    Element getRoot();

    void refresh();
}
