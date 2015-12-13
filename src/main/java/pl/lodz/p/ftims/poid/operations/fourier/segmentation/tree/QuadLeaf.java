package main.java.pl.lodz.p.ftims.poid.operations.fourier.segmentation.tree;

/**
 * Created by <a href="mailto:171131@edu.p.lodz.pl">Andrzej Lisowski</a> on 13.12.15.
 */
import java.util.ArrayList;

public class QuadLeaf<T> {
    public final double x;
    public final double y;
    public final ArrayList<T> values;

    public QuadLeaf(double x, double y, T value) {
        this.x = x;
        this.y = y;
        this.values = new ArrayList<T>(1);
        this.values.add(value);
    }
}