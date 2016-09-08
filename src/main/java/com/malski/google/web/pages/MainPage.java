package com.malski.google.web.pages;

import com.malski.core.web.annotations.PageCheck;
import com.malski.core.web.elements.Input;
import com.malski.core.web.view.Page;
import org.openqa.selenium.support.FindBy;

import static com.malski.core.utils.TestContext.getBrowser;

@PageCheck(url = "https://www.google.com")
public class MainPage extends Page {
    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "input[type='submit'][jsaction='sf.chk']")
    private Input searchButton;

    public MainPage open() {
        getBrowser().get(getUrl());
        return this;
    }

    public ResultsPage search(String phrase) {
        searchInput.fill(phrase);
        ResultsPage resultsPage = new ResultsPage();
        return new ResultsPage().waitForResults();
    }
}
