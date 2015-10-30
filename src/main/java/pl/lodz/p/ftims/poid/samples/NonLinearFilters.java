package main.java.pl.lodz.p.ftims.poid.samples;

import main.java.pl.lodz.p.ftims.poid.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear.RobertsOperator1;
import main.java.pl.lodz.p.ftims.poid.operations.filters.nonlinear.RobertsOperator2;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class NonLinearFilters {
    public static final Map<String, Transformable> FILTERS = new LinkedHashMap<>();
    static{
        FILTERS.put("Roberts Operator 1", new RobertsOperator1());
        FILTERS.put("Roberts Operator 2", new RobertsOperator2());
        FILTERS.put("Sobel Operator", null);
        FILTERS.put("Kirsh Operator", null);
        FILTERS.put("Rosenfeld Operator", null);
        FILTERS.put("Uolis Operator", null);
    }

    private NonLinearFilters(){

    }
}
