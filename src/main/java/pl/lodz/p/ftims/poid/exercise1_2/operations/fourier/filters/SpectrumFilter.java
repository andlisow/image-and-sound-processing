package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Complex;

/**
 * @author alisowsk
 */
public class SpectrumFilter implements FourierFilter {
    private int k;
    private int l;

    public SpectrumFilter() {
        this(0,0);
    }

    public SpectrumFilter(int k, int l) {
        this.k = k;
        this.l = l;
    }

    @Override
    public void applyFilter(Complex[][] complexImage) {
        int M = complexImage.length;
        int N = complexImage[0].length;

        for(int m=0; m<M ; m++) {
            for(int n=0; n<N; n++) {
                complexImage[m][n] = complexImage[m][n].times(Complex.fromPolar(1,
                        ((-(double) n * k * 2.0d) / (double) M + (-(double) m * l * 2.0d) / (double) N + (double) (k + l)) * Math.PI));
            }
        }

    }

    @Override
    public void setMinOrK(int minOrK) {
        this.k = minOrK;
    }

    @Override
    public void setMaxOrL(int maxOrL) {
        this.l = maxOrL;
    }
}
