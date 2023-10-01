package org.example.utils.images;

import java.awt.image.BufferedImage;
import java.util.function.IntUnaryOperator;

public class RGBMaster {

    private final int width, height;
    private final BufferedImage image;

    public RGBMaster(BufferedImage _image) {
        image = _image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void applyFilter(IntUnaryOperator filterOperation) {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                image.setRGB(j, i,
                          filterOperation.applyAsInt(image.getRGB(j, i))
                      );
            }
        }
    }

    //тут я нашел способ управлять цветами напрямую
    public static int greyScale(int argb) {
        int red = (argb >> 16) & 0xFF,
            green = (argb >> 8) & 0xFF,
            blue = argb & 0xFF;

        int avg = (red + green + blue)/3;

        return (avg << 16) | (avg << 8) | avg;
    }

    //этот фильтр вы тоже можете протестировать
    public static int brightened(int argb, int brightness) {
        int red = Math.max(0, Math.min(((argb >> 16) & 0xFF) + brightness, 255)),
            green = Math.max(0, Math.min(((argb >> 8) & 0xFF) + brightness, 255)),
            blue = Math.max(0, Math.min((argb & 0xFF) + brightness, 255));

        return (red << 16) | (green << 8) | blue;
    }
}
