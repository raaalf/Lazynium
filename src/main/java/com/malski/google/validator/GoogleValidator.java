package com.malski.google.validator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

public class GoogleValidator {

    public void checkResultsText(List<String> results, String texts) {
        //in assertJ no simple solution -> assertThat(actual, everyItem(containsString("alex")));
        assertThat(results, everyItem(containsString(texts)));
    }
}
