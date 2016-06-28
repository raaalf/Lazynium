package com.malski.google.web.modules;

import com.malski.core.web.page.api.WebModule;

public interface SearchForm extends WebModule {
    SearchForm searchFor(String phrase);
}
