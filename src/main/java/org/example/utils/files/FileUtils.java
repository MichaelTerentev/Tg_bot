package org.example.utils.files;

import org.example.utils.images.RGBMaster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.IntUnaryOperator;

public final class FileUtils {

    private FileUtils() {}

    public static void saveImageByURL(String url, String filePath) throws IOException {
        URL urlModel = new URL(url);

        InputStream inputStream = urlModel.openStream();
        OutputStream outputStream = new FileOutputStream(filePath);

        byte[] b = new byte[2048];
        int length;

        while((length = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }

    public static BufferedImage getImage(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    public static void saveImage(BufferedImage image, String filePath) throws IOException {
        ImageIO.write(image, "png", new File(filePath));
    }

    public static void processImage(String filePath, IntUnaryOperator filterOperation) throws IOException {
        RGBMaster rgbMaster = new RGBMaster(getImage(filePath));

        rgbMaster.applyFilter(filterOperation);

        saveImage(rgbMaster.getImage(), filePath);
    }
}
