package com.malski.glue.google;

import com.malski.google.web.pages.MainPage;
import com.malski.google.web.pages.ResultsPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class GoogleSteps {

    @Given("^I open google main page$")
    public void openGoogleMainPage() {
        new MainPage().
                open();
    }

    @When("^I search for \"(.+)\"$")
    public void searchFor(String phrase) {
        new MainPage().
                searchFor(phrase);
    }

    @Then("^verify that page \"(.+)\" is in results$")
    public void verifyThatPageIsInResults(String pageName) {
        assertThat(new ResultsPage().getResultTitles(), everyItem(containsString(pageName)));
    }

    @When("^I research for \"(.+)\"$")
    public void researchFor(String phrase) {
        new ResultsPage().searchFor(phrase);
    }
}
