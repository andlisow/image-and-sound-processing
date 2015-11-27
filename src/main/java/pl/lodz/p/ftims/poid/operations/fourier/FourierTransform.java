package main.java.pl.lodz.p.ftims.poid.operations.fourier;

import main.java.pl.lodz.p.ftims.poid.model.Complex;
import main.java.pl.lodz.p.ftims.poid.model.Image;
import static main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.operations.fourier.filters.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class FourierTransform implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(FourierTransform.class);

    private FourierFilter filter;

    public FourierTransform(){

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

            Complex[][] afterForwardComplex = runDif2d(srcComplex);

            //saveSpectrum();
            //swap
            //swap

            swapQuadrants(afterForwardComplex);
            filter = new BandPassFilter(1,254);
            if(null != filter){
                filter.applyFilter(afterForwardComplex);
            }
            swapQuadrants(afterForwardComplex);

            //saveSpectrum();
            //swap
            //swap

            Complex[][] afterInverseComplex = runIDif2d(afterForwardComplex);

            double normalisedResult[][] = getPixelValues(afterInverseComplex, false);

            //normalisedResult = swapQuadrants(normalisedResult);

            for(int x=0; x<size; x++){
                for(int y=0; y<size; y++){
                    resultImage.getPixels()[x][y].setColor(color, (int) normalisedResult[x][y]);
                }
            }
        }

        return resultImage;
    }

    private Complex[][] runDif2d(Complex[][] srcComplex) {
        int size = srcComplex.length;
        Complex[][] afterRowTransformComplex = new Complex[size][size];
        Complex[][] afterColTransformComplex = new Complex[size][size];

        for(int x=0; x<size; x++){
            Complex[] complex = srcComplex[x];
            afterRowTransformComplex[x] = dif1d(complex);
        }

        afterRowTransformComplex = swap(afterRowTransformComplex);

        for(int x=0; x<size; x++){
            Complex[] complex = afterRowTransformComplex[x];
            afterColTransformComplex[x] = dif1d(complex);
        }

        return afterColTransformComplex;
    }

    private Complex[][] runIDif2d(Complex[][] afterForwardComplex) {
        int size = afterForwardComplex.length;
        Complex[][] afterRowTransformComplex = new Complex[size][size];
        Complex[][] afterColTransformComplex = new Complex[size][size];

        for(int x=0; x<size; x++){
            Complex[] complex = afterForwardComplex[x];
            afterRowTransformComplex[x] = iDiff1d(complex);
        }

        afterRowTransformComplex = swap(afterRowTransformComplex);

        for(int x=0; x<size; x++){
            Complex[] complex = afterRowTransformComplex[x];
            afterColTransformComplex[x] = iDiff1d(complex);
        }

        return afterColTransformComplex;
    }

    public static Complex[] iDiff1d(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = FourierTransform.dif1d(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;

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

    private double[][] getPixelValues(Complex[][] outputComplex, boolean normalize) {
        int size = outputComplex.length;
        double res[][] = new double[size][size];
        for(int x=0; x<size; x++){
            for(int y=0; y<size; y++){
                res[x][y] = outputComplex[x][y].re();
            }
        }
        if(normalize){
            res = normalise(res);
        }
        return res;
    }

    private double [][] normalise(double[][] values){
        int size = values.length;
        double curMin = values[0][0];
        double curMax = values[0][0];
        for(int x=0;x<size;x++){
            for(int y=0; y<size;y++){
                if(curMax < values[x][y]){
                    curMax = values[x][y];
                }
                if(curMin > values[x][y]){
                    curMin = values[x][y];
                }
            }
        }

        for(int x=0;x<size;x++){
            for(int y=0; y<size;y++) {
                values[x][y] = 255 * Math.log(values[x][y] + 1) / Math.log(curMax + 1);
            }
        }
        return values;
    }

    private Complex[][] swap(Complex[][] middleComplex) {
        int size = middleComplex.length;
        Complex [][] newTab = new Complex[size][size];
        for(int x=0; x<size; x++){
            for(int y=0; y<size; y++){
                newTab[x][y] = middleComplex[y][x];
            }
        }
        return newTab;
    }

    public static Complex[] dif1d(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1){
            return new Complex[] { x[0] };
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) {
            LOG.error("N has to be the power of 2");
        }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = dif1d(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = dif1d(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
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
}