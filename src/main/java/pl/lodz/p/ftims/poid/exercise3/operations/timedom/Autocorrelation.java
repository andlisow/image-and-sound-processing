package main.java.pl.lodz.p.ftims.poid.exercise3.operations.timedom;

import com.sun.media.sound.WaveFileWriter;
import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alisowsk
 */
public class Autocorrelation implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(Autocorrelation.class);

    @Override
    public WavFile process(WavFile wavFile) {
        LOG.info("Starting autocorrelation");
        int N = (int) wavFile.getNumFrames();
        int numberOfFrames = (int) wavFile.getNumFrames();
        int sampleRate = (int) wavFile.getSampleRate();
        int[] bufferWav = new int[N];
        String name = wavFile.getName();
        try {
            wavFile.readFrames(bufferWav, N);
            wavFile.close();
        } catch (Exception e) {
            LOG.error("Unexpected error has occurred when reading frames from sound", e);
        }



        int[][] okna = chunkArray(bufferWav, 4050);
        N = 4050;

        List<Integer> frequencies = new ArrayList<>();

        for(int[] buffer : okna) {

            long[] autocorrelation = new long[N];

            for (int m = 1; m < autocorrelation.length; m++) {
                long sum = 0;
                for (int n = 0; n < autocorrelation.length - m; n++) {
                    sum += buffer[n] * buffer[n + m];
                }
                autocorrelation[m - 1] = sum;
            }

            long localMaxIndex = findLocalMax(autocorrelation);
            int frequency = (int)(sampleRate / localMaxIndex);

//            LOG.info("Sample rate: " + N);
//            LOG.info("Global maximum: " + autocorrelation[0]);
//            LOG.info("Local maximum: " + autocorrelation[localMaxIndex]);
//            LOG.info("Local maximum index: " + localMaxIndex);
//            LOG.info("Frequency: " + frequency);
            System.err.println("VALUEL " + frequency);
//            LOG.info("Autocorrelation has finished");


            frequencies.add(frequency);
        }
        try {
            saveSound(frequencies, name, numberOfFrames,sampleRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int[][] chunkArray(int[] array, int chunkSize) {
        int numOfChunks = (int)Math.ceil((int)array.length / chunkSize);
        int[][] output = new int[numOfChunks][];

        for(int i = 0; i < numOfChunks; ++i) {
            int start = i * chunkSize;
            int length = Math.min(array.length - start, chunkSize);

            int[] temp = new int[length];
            System.arraycopy(array, start, temp, 0, length);
            output[i] = temp;
        }

        return output;
    }

    private void saveSound(List<Integer> frequencies, String name, int totalFrames, int sampleRate) throws Exception {
        byte[] pcm_data= new byte[totalFrames];


        WavFile wavFile = WavFile.newWavFile(new File("/home/andrzej/poid/transformed_" + name),1, totalFrames, 8, sampleRate);

        int prevFreq=0;
        for(int i=0; i<frequencies.size(); i++){
            double[] buffer = new double[4050];
            int frequency = frequencies.get(i);
            if(frequency==0){
                frequency = prevFreq;
            } else {
                prevFreq = frequency;
            }
            double L1 = (double)sampleRate/frequency;
            for(int j=0;j<4050;j++){
                buffer[j]= Math.sin(2 * Math.PI *  j / L1);
            }
            wavFile.writeFrames(buffer, 4050);
        }

    }


    private static long findLocalMax(long[] autocorrelation) {
        double globalMax = autocorrelation[0];

        double tempLocalMax = 0;
        int localMaxIndex = Integer.MAX_VALUE;
        double localMin = globalMax;

        boolean falling =true;
        for (int i = 1; i < autocorrelation.length; i++) {
            double cur = autocorrelation[i];
            if(falling) {
                if (cur < localMin) {
                    localMin = cur;
                } else if ((globalMax - localMin) * 0.98 > globalMax - cur) {
                    falling = false;
                    tempLocalMax = cur;
                }
            } else {
                if (cur > tempLocalMax) {
                    tempLocalMax = cur;
                    localMaxIndex = i;
                } else if (1.02 * tempLocalMax > globalMax){
                    return localMaxIndex;
                }
            }
        }

        return Integer.MAX_VALUE;
    }

//    //FIXME: natural sounds give too big freq, they leave method too early
//    private int findLocalMax(double[] autocorrelation) {
//        long now = Long.MAX_VALUE, prev, next, minForPitch;
//        minForPitch = (long)(autocorrelation[0] * 0.98);
//        for (int i = 2; i < autocorrelation.length - 1; i++) {
//
//            prev = (long) autocorrelation[i - 1];
//            now = (long) autocorrelation[i];
//            next = (long) autocorrelation[i + 1];
//            if (prev < now && now > next && now >= minForPitch) {
//                now = i;
//                break;
//            } else
//                now = Long.MAX_VALUE;
//        }
//
//
//
//        return (int) now;
//    }
}
