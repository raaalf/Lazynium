package com.malski.core.utils;

import org.apache.log4j.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.*;
import static org.monte.screenrecorder.ScreenRecorder.ENCODING_BLACK_CURSOR;

public class VideoRecorder {
    private static Logger log = Logger.getLogger(VideoRecorder.class);
    private TbeScreenRecorder screenRecorder;

    private TbeScreenRecorder getRecorder(String fileName) throws IOException, AWTException {
        File file = new File(TestContext.getConfig().getResourceDirPath() + "../../../target");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        return new TbeScreenRecorder(getGraphicConfig(), captureSize,
                new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_QUICKTIME),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_JPEG,
                        CompressorNameKey, ENCODING_QUICKTIME_JPEG,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15)),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_BLACK_CURSOR,
                        FrameRateKey, Rational.valueOf(30)),
                null, file, fileName);
    }

    private GraphicsConfiguration getGraphicConfig() {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
    }

    public void start(String scenarioName) {
        try {
            screenRecorder = getRecorder(prepareFileName(scenarioName));
            screenRecorder.start();
        } catch (IOException | AWTException e) {
            log.error("Error when video recording was starting: " + e.getMessage());
        }
    }

    public void stop() {
        try {
            this.screenRecorder.stop();
            log.info("New video created: " + screenRecorder.getCreatedMovieFilePath());
        } catch (IOException e) {
            log.error("Error while video recording was ending: " + e.getMessage());
        }
    }

    private String prepareFileName(String scenarioName) {
        return scenarioName.replaceAll("\\s", "_");
    }

    private class TbeScreenRecorder extends ScreenRecorder {
        String name;
        String format;

        TbeScreenRecorder(GraphicsConfiguration cfg,
                          Rectangle captureArea, Format fileFormat, Format screenFormat,
                          Format mouseFormat, Format audioFormat, File movieFolder,
                          String name) throws IOException, AWTException {
            super(cfg, captureArea, fileFormat, screenFormat, mouseFormat,
                    audioFormat, movieFolder);
            this.name = name;
        }

        String getCreatedMovieFilePath() {
            return new File(movieFolder, name + "." + format).getAbsolutePath();
        }

        @Override
        protected File createMovieFile(Format fileFormat) throws IOException {
            if (!movieFolder.exists()) {
                movieFolder.mkdirs();
            } else if (!movieFolder.isDirectory()) {
                throw new IOException("\"" + movieFolder + "\" is not a directory.");
            }
            this.format = Registry.getInstance().getExtension(fileFormat);
            return new File(movieFolder, name + "." + format);
        }
    }
}
