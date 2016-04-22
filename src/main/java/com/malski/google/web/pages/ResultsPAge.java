package com.malski.google.web.pages;

import com.malski.core.web.annotations.Module;
import com.malski.core.web.annotations.PageInfo;
import com.malski.core.web.elements.Element;
import com.malski.core.web.elements.Elements;
import com.malski.core.web.page.Page;
import com.malski.google.web.api.SearchWithGoogle;
import com.malski.google.web.modules.SearchForm;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

@PageInfo(check = "https://www.google\\.com/\\?gfe_rd\\=cr\\&ei\\=")
public class ResultsPage extends Page implements SearchWithGoogle {

    @FindBys({@FindBy(xpath = "//div[@id='rso']//div[@class='rc']/*[@class='r']/a")})
    private Elements<Element> results;

    @Module
    private SearchForm searchForm;

    public ResultsPage() {
    }

    public SearchForm getSearchForm() {
        return searchForm;
    }

    @Override
    public ResultsPage searchFor(String phrase) {
        getSearchForm().searchFor(phrase);
        return this;
    }

    public void waitForResults() {
        results.waitUntilAnyVisible();
    }

    public List<String> getResultTitles() {
        return results.getTexts();
    }
}
