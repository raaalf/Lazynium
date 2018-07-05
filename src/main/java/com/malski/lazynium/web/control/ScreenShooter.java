package com.malski.lazynium.web.control;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenShooter {
    private AShot shooter;
    private WebDriver driver;

    public ScreenShooter(WebDriver driver) {
        shooter = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100));
        this.driver = driver;
    }

    public AShot getShooter() {
        return shooter;
    }

    public byte[] getScreenshotAsByteArray() throws WebDriverException {
        return convertToByteArray(getShooter().takeScreenshot(driver).getImage());
    }

    private byte[] convertToByteArray(BufferedImage originalImage) {
        byte[] imageInByte = new byte[0];
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageInByte;
    }
}
