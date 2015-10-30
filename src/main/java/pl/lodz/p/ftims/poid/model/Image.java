package main.java.pl.lodz.p.ftims.poid.model;

/**
 * @author alisowsk
 */
public class Image {
    private String name;
    private Pixel[][] pixels;
    private int width;
    private int height;

    public Image(String imgName, Pixel[][] pixels, int width, int height) {
        this.name = imgName;
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public Image(Image other) {
        this.name = other.name;
        this.width = other.width;
        this.height = other.height;
        this.pixels = new Pixel[other.width][other.height];
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                this.pixels[x][y] = new Pixel(other.pixels[x][y]);
            }
        }
    }

    public Pixel getPixel(int x, int y){
        return pixels[x][y];
    }

    public String getName() {
        return name;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
