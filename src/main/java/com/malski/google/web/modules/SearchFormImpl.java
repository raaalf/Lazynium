package com.malski.google.web.modules;

import com.malski.core.web.elements.Input;
import com.malski.core.web.factory.LazyLocator;
import com.malski.core.web.page.Module;
import org.openqa.selenium.support.FindBy;

public class SearchFormImpl extends Module implements SearchForm {

    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "input[type='submit'][jsaction='sf.chk']")
    private Input searchButton;

    public SearchFormImpl() {
        super();
    }

    public SearchFormImpl(LazyLocator locator) {
        super(locator);
    }

    public SearchForm searchFor(String phrase) {
        searchInput.fill(phrase);
        return this;
    }
}
