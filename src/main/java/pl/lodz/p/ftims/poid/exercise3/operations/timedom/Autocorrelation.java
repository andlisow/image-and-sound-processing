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
        String name = wavFile.getName();
        try {
            wavFile.readFrames(buffer, N);
            wavFile.close();
        } catch (Exception e) {
            LOG.error("Unexpected error has occurred when reading frames from sound", e);
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
        double frequency = N / (localMaxIndex);

        LOG.info("Sample rate: " + N);
        LOG.info("Global maximum: " + autocorrelation[0]);
        LOG.info("Local maximum: " + autocorrelation[localMaxIndex]);
        LOG.info("Local maximum index: " + localMaxIndex);
        LOG.info("Frequency: " + frequency);

        LOG.info("Autocorrelation has finished");

        saveSound (frequency, name);

        return null;
    }

    private void saveSound(double frequency, String name) {
        byte[] pcm_data= new byte[2 * 44100];
        double L1      = 2 * 44100.0/frequency;
        for(int i=0;i<pcm_data.length;i++){
            pcm_data[i]=  (byte)(55*Math.sin((i/L1)*Math.PI*2));
        }

        AudioFormat frmt= new AudioFormat(44100,8,1,true,false);
        AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(pcm_data),frmt,pcm_data.length);

        try {
            AudioSystem.write(ais,AudioFileFormat.Type.WAVE,new File("/home/andrzej/poid/transformed_" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
