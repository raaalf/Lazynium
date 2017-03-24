package com.malski.core.web.factory;

import com.malski.core.utils.PropertyKey;
import com.malski.core.utils.TestContext;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.awt.*;

public class MultiDisplayHandler {
    protected final Logger log = Logger.getLogger(getClass());
    private GraphicsDevice[] devices;

    public MultiDisplayHandler() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        devices = env.getScreenDevices();
        ArrayUtils.reverse(devices);
    }

    public void moveBrowserToDisplay(WebDriver driver) {
        String multiScreen = TestContext.getPropertyByKey(PropertyKey.DISPLAY_DIRECTION);
        int screensCount = getNumberOfScreens();
        if (!StringUtils.isBlank(multiScreen) && screensCount > 1) {
            Point point;
            switch (multiScreen.toLowerCase()) {
                case "right":
                    point = getDisplayBasedOnCondition((b, m) -> (b.x <= (m.x - b.width)), multiScreen);
                    break;
                case "left":
                    point = getDisplayBasedOnCondition((b, m) -> (b.x >= m.width), multiScreen);
                    break;
                case "up":
                case "top":
                    point = getDisplayBasedOnCondition((b, m) -> (b.y >= (m.y - b.height)), multiScreen);
                    break;
                case "down":
                case "bottom":
                    point = getDisplayBasedOnCondition((b, m) -> (b.y >= m.height), multiScreen);
                    break;
                case "second":
                    point = getNthDisplay(2);
                    break;
                case "last":
                    point = getNthDisplay(screensCount);
                    break;
                default:
                    point = new Point(0, 0);
            }
            driver.manage().window().setPosition(point);
        }
    }

    private int getNumberOfScreens() {
        try {
            return devices.length;
        } catch (HeadlessException e) {
            return 1;
        }
    }

    private Point getNthDisplay(int index) {
        try {
            Rectangle bounds = devices[index - 1].getDefaultConfiguration().getBounds();
            return new Point(bounds.x + 10, bounds.y + 10);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("No display with index " + index, e);
        }
    }

    private Point getDisplayBasedOnCondition(MatchingFunction<Rectangle, Rectangle, Boolean> condition, String direction) {
        Rectangle mainBounds = getMainMonitorBounds();
        for (GraphicsDevice device : devices) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            if (condition.apply(bounds, mainBounds)) {
                return new Point(bounds.x + 10, bounds.y + 10);
            }
        }
        log.warn("Couldn't find display on " + direction + ". Get params of second one.");
        return getNthDisplay(2);
    }

    private Rectangle getMainMonitorBounds() {
        for (GraphicsDevice device : devices) {
            Rectangle bounds = device.getDefaultConfiguration().getBounds();
            if (bounds.getX() == 0 && bounds.getY() == 0) {
                return bounds;
            }
        }
        throw new HeadlessException("Couldn't find main display!!!");
    }

    private interface MatchingFunction<A, B, R> {
        R apply(A a, B b);
    }
}
