package main.java.pl.lodz.p.ftims.poid.operations.fourier.filters;

import main.java.pl.lodz.p.ftims.poid.model.Complex;

/**
 * Created by <a href="mailto:171131@edu.p.lodz.pl">Andrzej Lisowski</a> on 27.11.15.
 */
public interface FourierFilter {

    void applyFilter(Complex[][] complexImage);
}
