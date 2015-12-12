package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;

/**
 * @author alisowsk
 */
public interface FourierFilter {

    void applyFilter(Complex[][] complexImage);

    default void setMinOrK(int minOrK){

    }

    default void setMaxOrL(int maxOrL){

    }
}
