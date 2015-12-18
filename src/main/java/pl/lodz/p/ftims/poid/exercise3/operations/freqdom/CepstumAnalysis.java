package main.java.pl.lodz.p.ftims.poid.exercise3.operations.freqdom;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;

/**
 * @author alisowsk
 */
public class CepstumAnalysis implements Transformable {

    @Override
    public WavFile process(WavFile wavFile) {
        return null;
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
