package com.malski.google.web.pages;

import com.malski.core.web.annotations.Module;
import com.malski.core.web.elements.api.Element;
import com.malski.core.web.elements.api.Elements;
import com.malski.core.web.page.impl.Page;
import com.malski.google.web.api.SearchWithGoogle;
import com.malski.google.web.modules.SearchForm;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ResultsPage extends Page implements SearchWithGoogle {

    @FindBy(xpath = "//div[@id='rso']//div[@class='rc']/*[@class='r']/a")
    private Elements<Element> results;

    @Module(@FindBy(id = "searchform"))
    private SearchForm searchForm;

    public ResultsPage() {
    }

    public SearchForm getSearchForm() {
        return searchForm;
    }

    @Override
    public ResultsPage searchFor(String phrase) {
        getSearchForm().searchFor(phrase);
        waitForResults();
        return this;
    }

    public void waitForResults() {
        results.waitUntilAnyVisible();
    }

    public List<String> getResultTitles() {
        return results.getTexts();
    }
}
