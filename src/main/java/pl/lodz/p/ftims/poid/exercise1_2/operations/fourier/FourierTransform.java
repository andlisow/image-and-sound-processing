package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters.FourierFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import static main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.FourierUtil.*;

/**
 * @author alisowsk
 */
public class FourierTransform implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(FourierTransform.class);

    private FourierFilter filter;

    private boolean applyHannWindow;

    public FourierTransform(){
        this.filter = null;
    }

    public FourierTransform(FourierFilter filter){
        this.filter = filter;
    }

    @Override
    public Image process(Image image) {
        int size = image.getHeight();
        Image resultImage = new Image(image);
        Map<RgbColor, Complex[][]> colorChannels = transformImgToComplex2DTable(image);

        for(RgbColor color : colorChannels.keySet()){
            Complex[][] srcComplex = colorChannels.get(color);

            if(applyHannWindow){
                for(int x=0; x<size; x++) {
                    Complex[] complex = srcComplex[x];
                    applyHanningWindow(complex);
                }
                srcComplex = swap2dDimensions(srcComplex);
                for(int x=0; x<size; x++) {
                    Complex[] complex = srcComplex[x];
                    applyHanningWindow(complex);
                }
                srcComplex = swap2dDimensions(srcComplex);
            }

            Complex[][] afterForwardComplex = fftDit2d(srcComplex);

            swapQuadrants(afterForwardComplex);
            saveSpectrum("before_filter" + image.getName(), afterForwardComplex, Spectrum.PHASE, color);
            saveSpectrum("before_filter" + image.getName(), afterForwardComplex, Spectrum.ABS, color);
            swapQuadrants(afterForwardComplex);

            //TODO button that opens folder with images
            //Desktop.getDesktop().open(file.getParentFile());

            swapQuadrants(afterForwardComplex);
            if(null != filter){
                filter.applyFilter(afterForwardComplex);
            }
            swapQuadrants(afterForwardComplex);

            if(null != filter){
                swapQuadrants(afterForwardComplex);
                saveSpectrum("after_filter" + image.getName(), afterForwardComplex, Spectrum.PHASE, color);
                saveSpectrum("after_filter" + image.getName(), afterForwardComplex, Spectrum.ABS, color);
                swapQuadrants(afterForwardComplex);
            }

            Complex[][] afterInverseComplex = iFftDit2d(afterForwardComplex);

            double normalisedResult[][] = getPixelValues(afterInverseComplex, false, Spectrum.NULL);

            //normalisedResult = swapQuadrants(normalisedResult);

            for(int x=0; x<size; x++){
                for(int y=0; y<size; y++){
                    resultImage.getPixels()[x][y].setColor(color, (int) normalisedResult[x][y]);
                }
            }
        }

        return resultImage;
    }

    private void saveSpectrum(String imgName, Complex[][] complexImage, Spectrum spectrum, RgbColor analysedColor) {
        double[][] pixelValues = getPixelValues(complexImage, true, spectrum);
        int size = pixelValues.length;
        Pixel[][] pixels = new Pixel[size][size];

        for(int x=0; x<size; x++){
            for(int y=0; y<size; y++){
                if(pixelValues[x][y] < 0){
                    pixelValues[x][y] = 0;
                }
                pixels[x][y] = new Pixel((int) pixelValues[x][y]);
            }
        }

        Image image = new Image(spectrum + "_" + analysedColor + "_" + imgName, pixels, 3);
        try {
            ImageUtil.saveImageToFile(image);
        } catch (IOException e) {
            LOG.error("An error has occurred while saving image", e);
        }
    }

    private void applyHanningWindow(Complex[] complexImage) {
        int n = complexImage.length;
        for (int x=0; x<n; x++) {
            double w = 0.5 * (1.0d - Math.cos((2.0d * Math.PI * x) / (n - 1)));
            double newReal = complexImage[x].getReal() * w;
            if(newReal > 255){
                newReal = 255;
            } else if (newReal < 0){
                newReal = 0;
            }
            complexImage[x].setReal(newReal);
        }
    }

    private Complex[][] swapQuadrants(Complex[][] complexImage) {
        int size = complexImage.length;

        for(int x=0; x<size/2; x++){
            for (int y=0; y<size/2; y++){
                Complex temp = complexImage[x][y];
                complexImage[x][y] = complexImage[x+size/2][y+size/2];
                complexImage[x+size/2][y+size/2] = temp;
            }
        }

        for(int x=size/2; x<size; x++){
            for (int y=0; y<size/2; y++){
                Complex temp = complexImage[x][y];
                complexImage[x][y] = complexImage[x-size/2][y+size/2];
                complexImage[x-size/2][y+size/2] = temp;
            }
        }

        return complexImage;
    }

    private double[][] getPixelValues(Complex[][] complexImage, boolean normalize, Spectrum spectrum) {
        final int rows = complexImage.length;
        final int cols = complexImage[0].length;

        double res[][] = new double[rows][cols];
        for(int x=0; x<rows; x++){
            for(int y=0; y<cols; y++){
                if(Spectrum.NULL == spectrum){
                    res[x][y] = complexImage[x][y].getReal();
                    if (res[x][y] < 0){
                        res[x][y] = 0;
                    } else if (res[x][y] > 255){
                        res[x][y] = 255;
                    }
                } else if(Spectrum.PHASE == spectrum){
                    res[x][y] = complexImage[x][y].phase();
                } else if(Spectrum.ABS == spectrum){
                    res[x][y] = complexImage[x][y].abs();
                }
            }
        }

        if(normalize){
            res = normalise(res);
        }

        return res;
    }

    private double[][] normalise(double[][] values){
        final int rows = values.length;
        final int cols = values[0].length;

        double curMin = values[0][0];
        double curMax = values[0][0];

        for(int x=0; x<rows; x++){
            for(int y=0; y<cols; y++){
                if(curMax < values[x][y]){
                    curMax = values[x][y];
                }
                if(curMin > values[x][y]){
                    curMin = values[x][y];
                }
            }
        }

        for(int x=0; x<rows; x++){
            for(int y=0; y<cols; y++) {
                values[x][y] = ImageConstants.MAX_PIXEL_VALUE * Math.log(values[x][y] + 1) / Math.log(curMax + 1);
            }
        }

        return values;
    }

    private Map<RgbColor, Complex[][]> transformImgToComplex2DTable(Image image) {
        Map<RgbColor, Complex[][]> res = new HashMap<>();
        for(RgbColor color : RgbColor.values()){
            Complex[][] complex2DTable = new Complex[image.getWidth()][image.getHeight()];
            for(int x=0; x<image.getWidth(); x++){
                for(int y=0; y<image.getHeight(); y++){
                    complex2DTable[x][y] = new Complex(image.getPixel(x,y).getColor(color), 0.0d);
                }
            }
            res.put(color, complex2DTable);
        }
        return res;
    }

    public void setApplyHannWindow(boolean applyHannWindow) {
        this.applyHannWindow = applyHannWindow;
    }

    private enum Spectrum {
        ABS, PHASE, NULL;
    }
}