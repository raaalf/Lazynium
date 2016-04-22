package com.malski.core.web.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WaitConditions {

    private WaitConditions() {
    }

//    public static ExpectedCondition<WebElement> pageLoaded() {
//        return new ExpectedCondition() {
//            int counter = 0;
//            until (page.execute_script("return document.readyState")=="complete" && page.has_no_css?("img[src*='img/waitingSpinner.gif']")) || x==max_tires #5 seconds
//            puts "Waiting for page load #{x} Ready state:#{page.execute_script('return document.readyState')} Spinner:#{page.has_no_css?("img[src*='img/waitingSpinner.gif']")}"
//            sleep(interval)
//            x+=1
//            end
//
//            public WebElement apply(WebDriver driver) {
//                return ExpectedConditions.findElement(locator, driver);
//            }
//
//            public String toString() {
//                return "presence of element located by: " + locator;
//            }
//        };
//    }

}
