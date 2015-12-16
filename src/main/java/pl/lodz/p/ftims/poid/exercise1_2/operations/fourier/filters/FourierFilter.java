package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Complex;

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
