package com.malski.google.validator;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class GoogleValidator {

    public void checkResultsText(List<String> results, String texts) {
        assertThat(results).contains(texts);
    }
}
