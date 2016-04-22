package com.malski.google.web.modules;

import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Input;
import com.malski.core.web.page.Module;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class SearchForm extends Module {

    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "input[type='submit'][jsaction='sf.chk']")
    private Input searchButton;

    public SearchForm() {
        super(By.id("searchform"));
    }

    public SearchForm(Element rootElement) {
        super(rootElement);
    }

    public SearchForm searchFor(String phrase) {
        searchInput.fill(phrase);
        searchButton.click();
        return this;
    }
}
