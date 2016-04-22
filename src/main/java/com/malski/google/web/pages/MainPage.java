package com.malski.google.web.pages;

import com.malski.core.web.annotations.PageInfo;
import com.malski.core.web.elements.Input;
import com.malski.core.web.page.Page;
import com.malski.google.web.api.SearchWithGoogle;
import org.openqa.selenium.support.FindBy;

@PageInfo(url = "https://www.google.com")
public class MainPage extends Page implements SearchWithGoogle {
    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "input[type='submit'][jsaction='sf.chk']")
    private Input searchButton;

    public MainPage() {
        super();
    }

    public MainPage open() {
        getBrowser().get(getUrl());
        return this;
    }

    @Override
    public ResultsPage searchFor(String phrase) {
        searchInput.fill(phrase);
        ResultsPage resultsPage = nextPage(ResultsPage.class);
        resultsPage.waitForResults();
        return resultsPage;
    }
}
