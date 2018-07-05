package com.malski.lazynium.web.control;

import com.malski.lazynium.utils.TestContext;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VideoRecorder implements Runnable {
    private Logger log = Logger.getLogger(getClass());

    private double frameRate;
    private IMediaWriter writer;
    private ScheduledExecutorService pool;
    private long startTime;
    private int frameCount = 0;
    private String mimeType;
    private String filePath;
    private TakesScreenshot screenShooter;
    private String destinationPath;
    private int divisor;

    VideoRecorder(WebDriver driver) {
        this.screenShooter = (TakesScreenshot) driver;
        divisor = getQualityDivisor(TestContext.config().videoQuality());
        this.destinationPath = new File(TestContext.config().resourceDirPath() + "../../../" + TestContext.config().videoDestinationPath()).getAbsolutePath();
        this.mimeType = TestContext.config().videMimeType();
        this.frameRate = TestContext.config().videoFrameRate();
    }

    private String prepareFileName(String scenarioName) {
        return scenarioName.replaceAll("\\s", "_").replaceAll("[\\\\/:*?\"<>|\\.,]", "");
    }

    public void start(String scenarioName) {
        FileHandler.clearOrCreateDirectory(destinationPath);
        String fileName = prepareFileName(scenarioName);
        this.filePath = destinationPath + File.separator + fileName + "." + getFileExtension(this.mimeType);
        writer = ToolFactory.makeWriter(this.filePath);
        Rectangle screenBounds = getScreenRectangle();
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, screenBounds.width / divisor, screenBounds.height / divisor);
        pool = Executors.newScheduledThreadPool(1);
        log.info(" Starting video recording.");
        startTime = System.nanoTime();
        pool.scheduleAtFixedRate(this, 0L, (long) (1000.0 / frameRate), TimeUnit.MILLISECONDS);
    }

    public void stop() {
        try {
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error when video recording was initiated.", e);
        }
        log.info(" Screen recording has ended. " + frameCount + " images recorded in video.");
        writer.close();
    }

    @Override
    public void run() {
        byte[] screenshot = screenShooter.getScreenshotAs(OutputType.BYTES);
        BufferedImage screen;
        try {
            screen = ImageIO.read(new ByteArrayInputStream(screenshot));
            BufferedImage bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);
            writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        } catch (IOException e) {
            log.error("Couldn't add frame: " + frameCount);
        }
        frameCount++;
    }

    private static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
        BufferedImage image;
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        else {
            image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }
        return image;
    }

    private Rectangle getScreenRectangle() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        return new Rectangle(0, 0, width, height);
    }

    private int getQualityDivisor(String quality) {
        switch (quality.toLowerCase()) {
            case "high":
                return 1;
            case "medium":
                return 2;
            case "low":
                return 4;
            default:
                throw new RuntimeException("Quality not recognized: " + quality);
        }
    }

    private String getFileExtension(String mimeType) {
        switch (mimeType) {
            case "video/mp4":
                return "mp4";
            case "video/avi":
                return "avi";
            case "video/quicktime":
                return "mov";
            default:
                throw new RuntimeException("MimeType not recognized: " + mimeType);
        }
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getVideoFilePath() {
        return this.filePath;
    }

    public boolean removeVideo() {
        File videoFile = new File(getVideoFilePath());
        boolean result = !videoFile.exists() || videoFile.delete();
        if (!result) {
            log.error("Couldn't remove video file: " + getVideoFilePath());
        }
        return result;
    }
}