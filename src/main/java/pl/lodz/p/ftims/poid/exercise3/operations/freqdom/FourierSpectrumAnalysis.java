package main.java.pl.lodz.p.ftims.poid.exercise3.operations.freqdom;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise3.utils.FourierTransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author alisowsk
 */
public class FourierSpectrumAnalysis implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(FourierSpectrumAnalysis.class);

    @Override
    public WavFile process(WavFile wavFile) {
        LOG.info("Starting Fourier spectrum analysis");
        int N = (int) wavFile.getNumFrames();
        double[] buffer = new double[N];
        try {
            wavFile.readFrames(buffer, N);
            wavFile.close();
        } catch (Exception e) {
            LOG.error("Unexpected error has occurred when reading frames from sound", e);
        }

        double[] bufferPowerOf2 = makeArrPowerOf2(buffer);
        Complex[] complexSound = transformSignalToComplex(bufferPowerOf2);
        Complex[] complexSoundDft = FourierTransformUtil.dif1d(complexSound);

        double maxreal=0, maxbs=0;
        for(int i=0; i<complexSoundDft.length; i++){
            if(complexSoundDft[i].getReal() > maxreal){
                maxreal = complexSoundDft[i].getReal();
            }
            if(complexSoundDft[i].abs() > maxreal){
                maxbs = complexSoundDft[i].abs();
            }
        }

        LOG.error("max real: "+maxreal);
        LOG.error("max abs: "+maxbs);

        return null;
    }

    private double[] makeArrPowerOf2(double[] array) {
        int N = array.length;
        int powerOfTwo = 2;

        while (N > powerOfTwo){
            powerOfTwo *= 2;
        }

        return Arrays.copyOf(array, powerOfTwo);
    }

    private Complex[] transformSignalToComplex(double[] buffer) {
        int size = buffer.length;
        Complex[] complex = new Complex[size];

        for(int i=0; i<size; i++){
            complex[i] = new Complex(buffer[i], 0.0d);
        }

        return complex;
    }

}
