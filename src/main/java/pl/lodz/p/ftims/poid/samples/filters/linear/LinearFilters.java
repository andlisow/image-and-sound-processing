package main.java.pl.lodz.p.ftims.poid.samples.filters.linear;

import main.java.pl.lodz.p.ftims.poid.operations.filters.linear.LinearFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class LinearFilters {
    public static final Map<String, int[][]> FILTERS = new LinkedHashMap<>();
    static{
        FILTERS.put("Low Pass mask 1", new int [][] {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}});
        FILTERS.put("Low Pass mask 2", new int [][] {{1, 1, 1}, {1, 2, 1}, {1, 1, 1}});
        FILTERS.put("Low Pass mask 3", new int [][] {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}});

        FILTERS.put("Enhance edge mask 1", new int [][] {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}});
        FILTERS.put("Enhance edge mask 2", new int [][] {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}});
        FILTERS.put("Enhance edge mask 3", new int [][] {{1, -2, 1}, {-2, 5, -2}, {1, -2, 1}});

        FILTERS.put("Retrieve details North", new int [][] {{1, 1, 1}, {1, -2, 1}, {-1, -1, -1}});
        FILTERS.put("Retrieve details North-East", new int [][] {{1, 1, 1}, {-1, -2, 1}, {-1, -1, 1}});
        FILTERS.put("Retrieve details East", new int [][] {{-1, 1, 1}, {-1, -2, 1}, {-1, 1, 1}});
        FILTERS.put("Retrieve details South-East", new int [][] {{-1, -1, 1}, {-1, -2, 1}, {1, 1, 1}});

        FILTERS.put("Retrieve details South", new int [][] {{-1, -1, -1}, {1, -2, 1}, {1, 1, 1}});
        FILTERS.put("Retrieve details South-West", new int [][] {{1, -1, -1}, {1, -2, -1}, {1, 1, 1}});
        FILTERS.put("Retrieve details West", new int [][] {{1, 1, -1}, {1, -2, -1}, {1, 1, -1}});
        FILTERS.put("Retrieve details North-West", new int [][] {{1, 1, 1}, {1, -2, -1}, {1, -1, -1}});

        FILTERS.put("Retrieve details Laplacian mask 1", new int [][] {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}});
        FILTERS.put("Retrieve details Laplacian mask 2", new int [][] {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}});
        FILTERS.put("Retrieve details Laplacian mask 3", new int [][] {{1, -2, 1}, {-2, 4, -2}, {1, -2, 1}});

        FILTERS.put("Line identification vertical", new int [][] {{-1, 2, -1}, {-1, 2, -1}, {-1, 2, -1}});
        FILTERS.put("Line identification horizontal", new int [][] {{-1, -1, -1}, {2, 2, 2}, {-1, -1, -1}});
        FILTERS.put("Line identification top-right -> bottom-left", new int [][] {{-1, -1, 2}, {-1, 2, -1}, {2, -1, -1}});
        FILTERS.put("Line identification top-left -> bottom-right", new int [][] {{2, -1, -1}, {-1, 2, -1}, {-1, -1, 2}});
    }

    private LinearFilters(){

    }
}
