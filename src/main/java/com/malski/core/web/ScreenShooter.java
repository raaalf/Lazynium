package com.malski.core.web;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.ScreenshotException;

import java.io.File;
import java.io.IOException;

public class ScreenShooter {
    private TakesScreenshot shooter;

    public ScreenShooter(WebDriver driver) {
        shooter = (TakesScreenshot) driver;
    }

    public void getScreenShot(String fileName) {
        File scrFile = shooter.getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(fileName);
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            throw new ScreenshotException(e.getMessage());
        }
    }
}
