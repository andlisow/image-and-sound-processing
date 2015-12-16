package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation.tree;

/**
 * @author alisowsk
 */
public class PixelPoint {
    private int value;
    private int x;
    private int y;

    public PixelPoint(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
