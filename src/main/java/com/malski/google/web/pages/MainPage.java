package com.malski.google.web.pages;

import com.malski.core.web.Browser;
import com.malski.core.web.elements.Input;
import com.malski.core.web.page.Page;
import com.malski.google.web.api.SearchWithGoogle;
import com.malski.google.web.modules.SearchForm;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends Page implements SearchWithGoogle {
    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "input[type='submit'][jsaction='sf.chk']")
    private Input searchButton;

    private SearchForm searchForm;

    public MainPage(Browser browser) {
        super(browser);
        searchForm = new SearchForm(browser);
    }

    @Override
    public void waitToLoad() {
        getWait().until(ExpectedConditions.presenceOfElementLocated(searchInput.getBy()));
    }

    public SearchForm getSearchForm() {
        return searchForm;
    }

    @Override
    public void searchFor(String phrase) {
        searchInput.fill(phrase);
    }
}
