package com.malski.core.mobile.view;

import com.malski.core.mobile.control.LazyMobileContext;
import com.malski.core.mobile.elements.Element;
import com.malski.core.mobile.factory.LazyLocator;
import com.malski.core.mobile.factory.LazyScreenFactory;
import org.apache.log4j.Logger;

public class Module extends LazyMobileContext implements View {
    protected final Logger log = Logger.getLogger(getClass());
    private Element rootElement;

    public Module() {
    }

    public void initialize(LazyLocator locator) {
        setRoot(locator);
        initElements();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Element getSearchContext() {
        return getRoot();
    }

    @Override
    public boolean refresh() {
        boolean result = getRoot().refresh();
        initElements();
        return result;
    }

    public Element getRoot() {
        return rootElement;
    }

    public void setRoot(LazyLocator locator) {
        this.rootElement = locator.getElement();
    }

    public void initElements() {
        LazyScreenFactory.initElements(this);
    }

}
