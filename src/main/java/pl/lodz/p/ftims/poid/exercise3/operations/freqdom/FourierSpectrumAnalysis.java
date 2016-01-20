package main.java.pl.lodz.p.ftims.poid.exercise3.operations.freqdom;

import main.java.pl.lodz.p.ftims.poid.exercise3.model.Complex;
import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise3.utils.FourierTransformUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * @author alisowsk
 */
public class FourierSpectrumAnalysis implements Transformable {
    private static final Logger LOG = LoggerFactory.getLogger(FourierSpectrumAnalysis.class);


    protected double[][] d;

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



        int[][] okna = chunkArray(bufferWav, 32768);
        N = 32768;

        List<Integer> frequencies = new ArrayList<>();

        for(int[] buffer : okna) {


            LOG.info("length: {}, numchannels: {}", buffer.length, wavFile.getNumChannels());

            Complex[] complexSound = transformSignalToComplex(buffer);


            double arg = (2 * Math.PI) / ((double) N - 1.0);

            for (int i = 0; i < N; ++i)
                //hammming window
                complexSound[i] = new Complex(buffer[i] * (0.54 - 0.46 * Math.cos(arg * (double) i)), 0);

            complexSound = FourierTransformUtil.dif1d(complexSound);


//        complexSound = Arrays.copyOfRange(complexSound, 0, N / 2);
            //cepstrum rzeczywiste i zespolone
            for (int i = 0; i < complexSound.length; ++i)
                complexSound[i] = new Complex(10.0 * Math.log10(complexSound[i].abs() + 1), 0);


            complexSound = FourierTransformUtil.dif1d(complexSound);
            complexSound = Arrays.copyOfRange(complexSound, 0, N / 4);


            d = new double[2][N];

//		for (int j=0; j<d.length; ++j)
            for (int i = 0; i < complexSound.length; ++i) {
                //power cepstrum
                //d[0][i]=Math.pow(csignal[i].abs(),2);
                d[0][i] = complexSound[i].abs();
            }


            double[] dd = d[0];
            System.err.println("1");
            LinkedList<Integer> pperiod = new LinkedList<Integer>();

            //RANGE
            int range = 10;

//		System.out.println("RANGE"+range);
            for (int i = range; i < dd.length - range; ++i) {
                int bigger = 0;
                //sprawdz czy jest to ,,dolina o zboczu wysokim na ,,range''
                //sprawdzamy wysokość, ale nie stromość zbocza - peaki są ostre
                for (int j = i - range; j < i + range; ++j) {
                    if (dd[j] <= dd[i] && i != j)
                        bigger++;
                }
                //sprawdz czy zbocza sa tak wysokie jak to zalozylismy
                if (bigger == (range * 2) - 1) {

                    pperiod.add(i);

//				i+=range-1;
                }
            }
            System.err.println("2");
            int max_ind = 0;

            //odrzucanie wysokich ale peakow ale nie stromych
            //musza opadac w obu kierunkach - nisko
            for (ListIterator<Integer> iter = pperiod.listIterator(); iter.hasNext(); ) {
                int i = iter.next(), j = 0, k = 0;
                //szukamy najniższego wartosci na zboczu lewym
                while (i - j - 1 >= 0) {
                    if ((dd[i - j - 1] <= dd[i - j]))
                        ++j;
                    else
                        break;
                }
                //szukamy najnizszej wartosci na zboczu prawym
                while (((i + k + 1) < dd.length)) {
                    if ((dd[i + k + 1] <= dd[i + k]))
                        ++k;
                    else
                        break;
                }


                double maxmin = Math.max(dd[i - j], dd[i + k]);
                if (maxmin > dd[i] * 0.2) {
                    iter.remove();
//				System.out.println(i+ "+"+j+ " " + dd[i-j]+" "+dd[i]+" "+dd[i+k]);
                } else d[1][i] = dd[i];

            }

            System.err.println("3");
            //progowanie co do największego peaku
            max_ind = Collections.max(pperiod, new MaxDataComp(dd));

            for (ListIterator<Integer> it = pperiod.listIterator(); it.hasNext(); ) {
                Integer num = (Integer) it.next();
                if (dd[num] > dd[max_ind] * 0.4) {
                    d[1][num] = dd[num];
                } else
                    it.remove();
            }


            System.err.println("4");
            int max_b, max_a;
//		max_b= max_ind;
            max_b = Collections.max(pperiod, new MaxDataComp(dd));
            int a = 0, b = 0;
            while (pperiod.size() > 1) {
                for (ListIterator del = pperiod.listIterator(); del.hasNext(); )
                    if ((Integer) del.next() == max_b) {
                        del.remove();
                        break;
                    }

                max_a = Collections.max(pperiod, new MaxDataComp(dd));

                a = max_a;
                b = max_b;
                if (a > b) {
                    int tmp = a;
                    a = b;
                    b = tmp;
                }
                System.out.println(a + " " + b);


                for (ListIterator<Integer> it = pperiod.listIterator(); it.hasNext(); ) {
                    Integer num = (Integer) it.next();
                    if (num < a || num > b)
                        it.remove();
                }

                max_b = max_a;

            }
            System.err.println("5");
            max_ind = Math.abs(b - a);
            if (max_ind == 0 && pperiod.size() == 1)
                max_ind = pperiod.get(0);
            else
                System.out.println(a + " " + b);


            frequencies.add((int)((double)wavFile.getSampleRate()/(double)max_ind));

        }
        try {
            saveSound(frequencies, name, numberOfFrames,sampleRate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private int makePowerOf2(int windowWidth) {
        int powerOfTwo = 2;

        while (windowWidth > powerOfTwo){
            powerOfTwo *= 2;
        }

        return powerOfTwo;
    }

    private Complex[] transformSignalToComplex(int[] buffer) {
        int size = buffer.length;
        Complex[] complex = new Complex[size];

        for(int i=0; i<size; i++){
            complex[i] = new Complex(buffer[i], 0.0d);
        }

        return complex;
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
            double[] buffer = new double[32768];
            int frequency = frequencies.get(i);
            if(frequency==0){
                frequency = prevFreq;
            } else {
                prevFreq = frequency;
            }
            double L1 = (double)sampleRate/frequency;
            for(int j=0;j<32768;j++){
                buffer[j]= Math.sin(2 * Math.PI *  j / L1);
            }
            wavFile.writeFrames(buffer, 32768);
        }

    }

















}
