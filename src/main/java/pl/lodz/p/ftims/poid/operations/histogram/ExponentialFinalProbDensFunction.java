package main.java.pl.lodz.p.ftims.poid.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel.RgbColor;

/**
 * @author alisowsk
 */
public class ExponentialFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public ExponentialFinalProbDensFunction() {
    }

    @Override
    public Image process(Image image) {
        return null;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, RgbColor c) {
        return 0;
    }
}
