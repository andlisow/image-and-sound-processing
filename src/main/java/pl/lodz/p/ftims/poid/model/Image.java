package main.java.pl.lodz.p.ftims.poid.model;

/**
 * @author alisowsk
 */
public class Image {
    private Pixel[][] pixels;

    public Image(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }
}
