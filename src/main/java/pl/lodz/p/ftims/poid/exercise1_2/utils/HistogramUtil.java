package main.java.pl.lodz.p.ftims.poid.exercise1_2.utils;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alisowsk
 */
public class HistogramUtil {
    private HistogramUtil(){

    }

    public static List<Histogram> prepareHistograms(Image image){
        List<Histogram> histograms = new ArrayList<>();
        for(RgbColor color : RgbColor.values()){
            histograms.add(getHistogram(image, color));
        }
        return histograms;
    }

    private static Histogram getHistogram(Image image, RgbColor color) {
        Histogram histogram = new Histogram(image.getName(), color);
        for(int x=0; x<image.getWidth();x++){
            for(int y=0; y<image.getHeight(); y++){
                histogram.getValues()[image.getPixel(x,y).getColor(color)]++;
            }
        }
        return histogram;
    }

}
