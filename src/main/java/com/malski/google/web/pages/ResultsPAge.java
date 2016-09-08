package com.malski.google.web.pages;

import com.malski.core.web.annotations.IModule;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.view.Page;
import com.malski.google.web.modules.SearchForm;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ResultsPage extends Page {

    @FindBy(xpath = "//div[@id='rso']//div[@class='rc']/*[@class='r']/a")
    private Elements<Element> results;

    @IModule(@FindBy(id = "searchform"))
    private SearchForm searchForm;

    public ResultsPage() {
    }

    public SearchForm getSearchForm() {
        return searchForm;
    }

    public ResultsPage search(String phrase) {
        getSearchForm().searchFor(phrase);
        waitForResults();
        return this;
    }

    public ResultsPage waitForResults() {
        results.waitUntilAnyVisible();
        return this;
    }

    public List<String> getResultTitles() {
        return results.getTexts();
    }
}
