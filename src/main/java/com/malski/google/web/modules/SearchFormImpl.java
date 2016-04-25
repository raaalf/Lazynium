package com.malski.google.web.modules;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Input;
import com.malski.core.web.page.WebModuleImpl;
import org.openqa.selenium.support.FindBy;

@FindBy(id = "searchform")
public class SearchFormImpl extends WebModuleImpl implements SearchForm {

    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "button.lsb[name='btnG']")
    private Element searchButton;

    public SearchForm searchFor(String phrase) {
        searchInput.fill(phrase);
        searchButton.click();
        return this;
    }
}
