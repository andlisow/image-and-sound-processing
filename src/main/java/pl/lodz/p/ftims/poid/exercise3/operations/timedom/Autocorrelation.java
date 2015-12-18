package main.java.pl.lodz.p.ftims.poid.exercise3.operations.timedom;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class Autocorrelation implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(Autocorrelation.class);

    @Override
    public WavFile process(WavFile wavFile) {
        LOG.info("Starting autocorrelation");
        int N = (int) wavFile.getNumFrames();
        double[] buffer = new double[N];
        try {
            wavFile.readFrames(buffer, N);
            wavFile.close();
        } catch (Exception e) {
            LOG.error("Unexpected error has occurred when analysing sound", e);
        }

        double[] autocorrelation = new double[N];

        for (int m = 0; m < N; m++) {
            double sum = 0;
            for (int n = 0; n < N; n++) {
                 sum += buffer[n] * buffer[(N + n + m) % N];
            }
            autocorrelation[m] = sum;
        }

        int localMaxIndex = findLocalMax(autocorrelation);

        LOG.info("Sample rate: " + N);
        LOG.info("Global maximum: " + autocorrelation[0]);
        LOG.info("Local maximum: " + autocorrelation[localMaxIndex]);
        LOG.info("Local maximum index: " + localMaxIndex);
        LOG.info("Frequency: " + N / (localMaxIndex));

        LOG.info("Autocorrelation has finished");
        //TODO generate sound
        return null;
    }

    //FIXME: natural sounds give too big freq, they leave method too early
    private int findLocalMax(double[] autocorrelation) {
        double globalMax = autocorrelation[0];

        double tempLocalMax = 0;
        int localMaxIndex = 0;
        double localMin = globalMax;

        boolean falling =true;
        for (int i = 1; i < autocorrelation.length; i++) {
            double cur = autocorrelation[i];
            if(falling) {
                if (cur < localMin) {
                    localMin = cur;
                } else if ((globalMax - localMin) * 0.9 > globalMax - cur) {
                    falling = false;
                    tempLocalMax = cur;
                }
            } else {
                if (cur > tempLocalMax) {
                    tempLocalMax = cur;
                    localMaxIndex = i;
                    //FIXME: do we really need this if below?
                } else if (1.02 * tempLocalMax > globalMax){
                    LOG.info("Local max:  " + tempLocalMax);
                    LOG.error("Local max index: " + localMaxIndex);
                    return localMaxIndex;
                }
            }
        }

        return localMaxIndex;
    }
}
