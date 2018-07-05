package com.malski.google.pages;

import com.malski.lazynium.web.annotations.PageCheck;
import com.malski.lazynium.web.elements.Input;
import com.malski.lazynium.web.view.Page;
import org.openqa.selenium.support.FindBy;

@PageCheck(url = "https://www.google.com")
public class MainPage extends Page {
    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "input[type='submit'][jsaction='sf.chk']")
    private Input searchButton;

    public MainPage open() {
        browser().get(url());
        return this;
    }

    public ResultsPage search(String phrase) {
        searchInput.fill(phrase);
        ResultsPage resultsPage = new ResultsPage();
        return new ResultsPage().waitForResults();
    }
}
