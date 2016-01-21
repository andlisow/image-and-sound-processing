package main.java.pl.lodz.p.ftims.poid.exercise3.operations.freqdom;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise3.utils.FourierTransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static main.java.pl.lodz.p.ftims.poid.exercise3.utils.SoundUtil.chunkArray;
import static main.java.pl.lodz.p.ftims.poid.exercise3.utils.SoundUtil.generateSound;

/**
 * @author alisowsk
 */
public class FourierSpectrumAnalysis implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(FourierSpectrumAnalysis.class);

    private int chunkSize = 44100;

    protected double[][] d;
    private static final int RANGE = 10;

    static public class DataComp implements Comparator<Integer> {

        public double[] d;

        public DataComp(double[] d) {
            this.d = d;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            return 0;
        }
    }

    static public class MaxDataComp extends DataComp {

        public MaxDataComp(double[] d) {super(d);}

        @Override
        public int compare(Integer o1, Integer o2) {
            return (d[o1] > d[o2] ) ? 1 : -1 ;
        }

    }

    @Override
    public WavFile process(WavFile wavFile) {
        LOG.info("Starting Fourier Transform");
        int numberOfFrames = (int) wavFile.getNumFrames();
        int sampleRate = (int) wavFile.getSampleRate();
        int[] bufferWav = new int[numberOfFrames];
        String name = wavFile.getName();
        try {
            wavFile.readFrames(bufferWav, numberOfFrames);
            wavFile.close();
        } catch (Exception e) {
            LOG.error("Unexpected error has occurred when reading frames from sound", e);
        }
        chunkSize = makePowerOf2(chunkSize);

        int[][] parts;
        if(numberOfFrames != 44100){
            parts= chunkArray(bufferWav, chunkSize);
        } else {
            parts = new int[1][];
            parts[0] = bufferWav;
        }

        List<Integer> frequencies = new ArrayList<>();

        for(int[] buffer : parts) {
            Complex[] complexSound = transformSignalToComplex(buffer);

            applyHammingWindow(buffer, complexSound);

            complexSound = FourierTransformUtil.dif1d(complexSound);

            for (int i = 0; i < complexSound.length; ++i) {
                complexSound[i] = new Complex(10.0 * Math.log10(complexSound[i].abs() + 1), 0);
            }

            complexSound = FourierTransformUtil.dif1d(complexSound);
            complexSound = Arrays.copyOfRange(complexSound, 0, chunkSize / 4);

            d = new double[2][chunkSize];

            for (int i = 0; i < complexSound.length; ++i) {
                d[0][i] = complexSound[i].abs();
            }
            double[] dd = d[0];
            LinkedList<Integer> pperiod = new LinkedList<>();

            for (int i = RANGE; i < dd.length - RANGE; ++i) {
                int bigger = 0;
                for (int j = i - RANGE; j < i + RANGE; ++j) {
                    if (dd[j] <= dd[i] && i != j) {
                        bigger++;
                    }
                }
                if (bigger == (RANGE * 2) - 1) {
                    pperiod.add(i);
                }
            }
            int maxInd = 0;

            rejectHighPeaksButNotRapid(dd, pperiod);

            maxInd = Collections.max(pperiod, new MaxDataComp(dd));

            for (ListIterator<Integer> it = pperiod.listIterator(); it.hasNext(); ) {
                Integer num = it.next();
                if (dd[num] > dd[maxInd] * 0.4) {
                    d[1][num] = dd[num];
                } else
                    it.remove();
            }


            int maxB, maxA;
            maxB = Collections.max(pperiod, new MaxDataComp(dd));
            int a = 0, b = 0;
            while (pperiod.size() > 1) {
                for (ListIterator del = pperiod.listIterator(); del.hasNext(); )
                    if ((Integer) del.next() == maxB) {
                        del.remove();
                        break;
                    }

                maxA = Collections.max(pperiod, new MaxDataComp(dd));

                a = maxA;
                b = maxB;
                if (a > b) {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                LOG.info("{} {}", a, b);


                for (ListIterator<Integer> it = pperiod.listIterator(); it.hasNext(); ) {
                    Integer num = (Integer) it.next();
                    if (num < a || num > b)
                        it.remove();
                }

                maxB = maxA;

            }
            maxInd = Math.abs(b - a);
            if (maxInd == 0 && pperiod.size() == 1){
                maxInd = pperiod.get(0);
            } else{
                LOG.info("{} {}", a, b);
            }

            int frequency = (int) (sampleRate/(double)maxInd);
            LOG.info("Frequency: {}", frequency);
            frequencies.add(frequency);

        }
        WavFile generatedSound = generateSound(chunkSize, numberOfFrames, sampleRate, name, frequencies);

        return null;
    }

    private void rejectHighPeaksButNotRapid(double[] dd, LinkedList<Integer> pperiod) {
        for (ListIterator<Integer> iter = pperiod.listIterator(); iter.hasNext(); ) {
            int i = iter.next(), j = 0, k = 0;
            j = findLowestValueLeftSide(dd, i, j);
            k = findLowestValueRightSide(dd, i, k);

            double maxmin = Math.max(dd[i - j], dd[i + k]);
            if (maxmin > dd[i] * 0.2) {
                iter.remove();
            } else{
                d[1][i] = dd[i];
            }
        }
    }

    private int findLowestValueRightSide(double[] dd, int i, int k) {
        while ((i + k + 1) < dd.length) {
            if (dd[i + k + 1] <= dd[i + k]){
                ++k;
            } else{
                break;
            }
        }
        return k;
    }

    private int findLowestValueLeftSide(double[] dd, int i, int j) {
        while (i - j - 1 >= 0) {
            if (dd[i - j - 1] <= dd[i - j]){
                ++j;
            } else {
                break;
            }
        }
        return j;
    }

    private void applyHammingWindow(int[] buffer, Complex[] complexSound) {
        double arg = (2 * Math.PI) / ((double) chunkSize - 1.0);
        for (int i = 0; i < chunkSize; ++i){
            complexSound[i] = new Complex(buffer[i] * (0.54 - 0.46 * Math.cos(arg * (double) i)), 0);
        }
    }

    private Complex[] transformSignalToComplex(int[] buffer) {
        int size = buffer.length;
        Complex[] complex = new Complex[size];

        for(int i=0; i<size; i++){
            complex[i] = new Complex(buffer[i], 0.0d);
        }

        return complex;
    }

    private int makePowerOf2(int windowWidth) {
        int powerOfTwo = 2;

        while (windowWidth > powerOfTwo){
            powerOfTwo *= 2;
        }

        return powerOfTwo;
    }

    @Override
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

}
