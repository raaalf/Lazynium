package com.malski.google.components;

import com.malski.lazynium.web.elements.Element;
import com.malski.lazynium.web.elements.Input;
import com.malski.lazynium.web.view.Component;
import org.openqa.selenium.support.FindBy;

public class SearchForm extends Component {

    @FindBy(id = "lst-ib")
    private Input searchInput;

    @FindBy(css = "button.lsb[name='btnG']")
    private Element searchButton;

    public SearchForm searchFor(String phrase) {
        searchInput.fill(phrase);
        searchButton.click();
        return this;
    }
}
