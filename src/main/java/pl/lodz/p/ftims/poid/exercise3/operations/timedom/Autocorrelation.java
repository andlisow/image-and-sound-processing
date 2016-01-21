package main.java.pl.lodz.p.ftims.poid.exercise3.operations.timedom;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static main.java.pl.lodz.p.ftims.poid.exercise3.utils.SoundUtil.chunkArray;
import static main.java.pl.lodz.p.ftims.poid.exercise3.utils.SoundUtil.generateSound;

/**
 * @author alisowsk
 */
public class Autocorrelation implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(Autocorrelation.class);

    private int chunkSize = 44100;

    @Override
    public WavFile process(WavFile wavFile) {
        LOG.info("Starting autocorrelation");
        int numberOfFrames = (int) wavFile.getNumFrames();
        int sampleRate = (int) wavFile.getSampleRate();
        int[] bufferWav = new int[numberOfFrames];
        LOG.info("Sample rate: " + sampleRate);
        LOG.info("Number of frames: " + numberOfFrames);
        LOG.info("Chunk size: "  + chunkSize);
        String name = wavFile.getName();
        try {
            wavFile.readFrames(bufferWav, numberOfFrames);
            wavFile.close();
        } catch (Exception e) {
            LOG.error("Unexpected error has occurred when reading frames from sound", e);
        }

        int[][] parts = chunkArray(bufferWav, chunkSize);

        List<Integer> frequencies = new ArrayList<>();

        for(int[] buffer : parts) {

            long[] autocorrelation = new long[chunkSize];

            for (int m = 1; m < autocorrelation.length; m++) {
                long sum = 0;
                for (int n = 0; n < autocorrelation.length - m; n++) {
                    sum += buffer[n] * buffer[n + m];
                }
                autocorrelation[m - 1] = sum;
            }

            long localMaxIndex = findLocalMax(autocorrelation);
            int frequency = (int)(sampleRate / localMaxIndex);

            LOG.info("Global maximum: " + autocorrelation[0]);
            LOG.info("Frequency: " + frequency);


            frequencies.add(frequency);
        }
        LOG.info("Autocorrelation has finished");
        WavFile generatedSound = generateSound(chunkSize, numberOfFrames, sampleRate, name, frequencies);

        return null;
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
                } else if ((globalMax - localMin) * 0.95 > globalMax - cur) {
                    falling = false;
                    tempLocalMax = cur;
                }
            } else {
                if (cur > tempLocalMax) {
                    tempLocalMax = cur;
                    localMaxIndex = i;
                } else if (1.05 * tempLocalMax > globalMax){
                    return localMaxIndex;
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    @Override
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

}
