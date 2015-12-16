package main.java.pl.lodz.p.ftims.poid.exercise1_2.model;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;

/**
 * Model of histogram.
 *
 * @author alisowsk
 */
public class Histogram {
    private String name;
    private final RgbColor color;
    private final int[] values;

    public Histogram(String name, RgbColor color) {
        this.name = name;
        this.color = color;
        this.values = new int[ImageConstants.NUMBER_OF_PIXEL_VALUES];
    }

    public String getName() {
        return name;
    }

    public RgbColor getColor() {
        return color;
    }

    public int[] getValues() {
        return values;
    }
}
