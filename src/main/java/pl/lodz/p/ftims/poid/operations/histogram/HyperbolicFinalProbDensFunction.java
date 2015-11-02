package main.java.pl.lodz.p.ftims.poid.operations.histogram;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.model.Pixel;

/**
 * @author alisowsk
 */
public class HyperbolicFinalProbDensFunction extends AbstractFinalProbDensFunction {
    public HyperbolicFinalProbDensFunction() {

    }

    @Override
    public Image process(Image image) {
        return null;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, Pixel.RgbColor c) {
        return 0;
    }
}