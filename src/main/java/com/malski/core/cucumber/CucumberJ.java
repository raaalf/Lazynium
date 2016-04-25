package com.malski.core.cucumber;

import com.malski.core.web.Browser;
import cucumber.api.junit.Cucumber;
import org.junit.runners.model.InitializationError;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Created by rmalski on 2016-04-25.
 */
public class CucumberJ extends Cucumber {
    Logger log = getLoggerOfClass();
    static Logger staticLogger = Logger.getLogger(CucumberJ.class);
    public static String resourceDirPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    public CucumberJ(Class clazz) throws InitializationError, IOException {
        super(clazz);
        // TODO initialize properties
        String browser = "chrome";
        TestContext.setBrowser(new Browser(browser));
    }

    private Logger getLoggerOfClass() {
        return Logger.getLogger(this.getClass().getName());
    }
}