package main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.filters;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class BasicFiltersMasks {
    public static final Map<String, Integer> MASKS = new LinkedHashMap<>();
    static{
        MASKS.put("3 x 3", 9);
        MASKS.put("5 x 5", 25);
        MASKS.put("7 x 7", 49);
        MASKS.put("9 x 9", 81);
        MASKS.put("11 x 11", 121);
        MASKS.put("13 x 13", 169);
    }

    private BasicFiltersMasks(){

    }
}
