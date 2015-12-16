package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Transformable;

import java.util.List;

/**
 * @author alisowsk
 */
public abstract class AbstractFinalProbDensFunction implements Transformable {
    protected int gMin;
    protected int gMax;
    protected List<Histogram> histograms;

    public AbstractFinalProbDensFunction() {
    }

    @Override
    public Image process(Image originalImage) {
        Image result = new Image(originalImage);
        for(int x = 0; x<originalImage.getWidth(); x++){
            for(int y = 0; y<originalImage.getHeight(); y++){
                result.getPixels()[x][y] = processSinglePixel(originalImage, x, y);
            }
        }
        return result;
    }

    private Pixel processSinglePixel(Image originalImage, int x, int y) {
        Pixel pixel = new Pixel();
        for(Histogram histogram : histograms){
            int newColorValue = processSingleColor(originalImage, x, y, histogram);
            pixel.setColor(histogram.getColor(), newColorValue);
        }
        return pixel;
    }

    protected abstract int processSingleColor(Image img, int x, int y, Histogram histogram);

    protected int calculateSum(int f, Histogram histogram) {
        int sum=0;
        for(int m=0; m<=f;m++){
            sum+=histogram.getValues()[m];
        }
        return sum;
    }

    public void setgMin(int gMin) {
        this.gMin = gMin;
    }

    public void setgMax(int gMax) {
        this.gMax = gMax;
    }

    public void setHistograms(List<Histogram> histograms) {
        this.histograms = histograms;
    }
}
