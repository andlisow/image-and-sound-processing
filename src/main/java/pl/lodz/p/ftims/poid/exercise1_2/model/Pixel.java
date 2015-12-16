package main.java.pl.lodz.p.ftims.poid.exercise1_2.model;

/**
 * @author alisowsk
 */
public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int gray;

    public int getGrayScale() {
        return (int) (0.299 * red  + 0.587 * green + 0.114 * blue);
    }

    public int getGray() {
        return red;
    }

    public enum RgbColor {
        RED, GREEN, BLUE;
    }

    public Pixel() {
    }

    public Pixel(int gray){
        this(gray, gray, gray);
    }

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Pixel(Pixel other) {
        this.red = other.red;
        this.green = other.green;
        this.blue = other.blue;
    }

    public void setColor(RgbColor color, int value){
        if(color == RgbColor.RED){
            setRed(value);
        } else if(color == RgbColor.GREEN){
            setGreen(value);
        } else {
            setBlue(value);
        }
    }

    public int getColor(RgbColor color){
        if(color == RgbColor.RED){
            return red;
        } else if(color == RgbColor.GREEN){
            return green;
        }
        return blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setGreen(int green) {
        this.green = green;
    }
}
