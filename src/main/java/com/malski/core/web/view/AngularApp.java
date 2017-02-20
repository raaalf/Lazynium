package com.malski.core.web.view;

import com.malski.core.utils.TestContext;
import com.malski.core.web.conditions.WaitConditions;
import com.malski.core.web.elements.Element;
import com.paulhammant.ngwebdriver.VariableNotInScopeException;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "[ng-app]")
public class AngularApp extends Component {

    public AngularApp() {
        initElements();
    }

    public void mutate(Element element, final String variable, final String value) {
        browser().jsExecutor().executeScript("angular.element(arguments[0]).scope()." + variable + " = " + value + ";" +
                "angular.element(arguments[1]).injector().get('$rootScope').$apply();", element, root());
    }

    public String retrieveJson(Element element, final String variable) {
        return (String) check(variable, browser().jsExecutor().executeScript(
                "return angular.toJson(angular.element(arguments[0]).scope()." + variable + ");", element));
    }

    private Object check(String variable, Object o) {
        if (o == null) {
            throw new VariableNotInScopeException("$scope variable '" + variable + "' not found in same scope as the element passed in.");
        }
        return o;
    }

    public Object retrieve(Element element, final String variable) {
        return check(variable, browser().jsExecutor().executeScript(
                "return angular.element(arguments[0]).scope()." + variable + ";", element));
    }

    public String retrieveAsString(Element element, final String variable) {
        return retrieve(element, variable).toString();
    }

    public Long retrieveAsLong(Element element, final String variable) {
        Object rv = retrieve(element, variable);
        if (rv instanceof Double) {
            return ((Double) rv).longValue();
        }
        return (Long) rv;
    }

    public void waitForAngular() {
        if (browser().isAlertPresent()) {
            return;
        }
        getWait(TestContext.config().angularTimeout()).until(WaitConditions.angularReady());
    }

    public String locationAbsUrl() {
        // TODO
//        return browser().jsExecutor().executeScript(
//                "var selector = '" + rootSelector + "';\n" +
//                        "\n" +
//                        ByAngular.functions.get("getLocationAbsUrl"));
        return null;
    }

    public Object evaluateScript(Element element, String script) {
        script = script.replace("$scope", "angular.element(arguments[0]).scope()");
        return browser().jsExecutor().executeScript(script, element);
    }
}