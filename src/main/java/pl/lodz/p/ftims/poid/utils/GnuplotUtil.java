package main.java.pl.lodz.p.ftims.poid.utils;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.terminal.ImageTerminal;
import main.java.pl.lodz.p.ftims.poid.model.Histogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.List;

/**
 * @author alisowsk
 */
public class GnuplotUtil {
    //TODO move to program args?
    private static final String DEFAULT_FILE_SAVE_PATH = "/home/andrzej/poid/";
    private static final Logger LOG = LoggerFactory.getLogger(GnuplotUtil.class);

    public static void saveHistograms(List<Histogram> histograms) {
        for(Histogram histogram : histograms){
            saveHistogram(histogram);
        }
    }

    //TODO refactor
    public static void saveHistogram(Histogram histogram){
        String dataFile = createTempDataFile(histogram);
        if("".equals(dataFile)){
            return;
        }

        int minX=0;
        int maxX=255;
        double boxwidth=0.25;
        ImageTerminal png = new ImageTerminal();
        String fullPath = DEFAULT_FILE_SAVE_PATH + histogram.getName().split("\\.")[0] + "_" + histogram.getColor();
        File file = new File(fullPath);
        try {
            file.createNewFile();
            png.processOutput(new FileInputStream(file));
        } catch (IOException ex) {
            LOG.error("An error has occurred when trying to create chart file", ex);
        }

        JavaPlot p = new JavaPlot();
        StringBuilder sb = new StringBuilder();
        sb.append("\"" + dataFile + "\"u 1:2 w boxes");
        p.addPlot(sb.toString());
        p.set("key", "left");
        p.set("style", "fill transparent solid 2 noborder");
        p.set("grid", "y");
        p.set("style", "histogram rowstacked");
        p.set("boxwidth", "" + boxwidth + "");
        p.set("style", "fill pattern border -1");
        p.set("xrange", "[" + (minX - 1) + ":" + (maxX + 1) + "]");
        p.getAxis("y").setLabel("Number of occurences");
        p.getAxis("x").setLabel("Values");
        p.set("xtics", "20");
        p.setTitle("Histogram of dataset " + histogram.getName());
        p.set("style", "data histograms");
        p.setTerminal(png);
        p.plot();

        try {
            ImageIO.write(png.getImage(), "png", file);
        } catch (IOException ex) {
            LOG.error("An error has occurred when trying to write to chart file", ex);
        }

        deleteTempDataFile(dataFile);
    }

    private static String createTempDataFile(Histogram histogram) {
        File dataFile = new File(DEFAULT_FILE_SAVE_PATH + "tempData.dat");
        try {
            PrintWriter printWriter = new PrintWriter(dataFile);
            for(int i=0; i<histogram.getValues().length;i++){
                printWriter.println(i + " " + histogram.getValues()[i]);
            }
            printWriter.close();
        } catch (FileNotFoundException ex) {
            LOG.error("An error has occurred when trying to create temporary data file", ex);
            return "";
        }
        return dataFile.getAbsolutePath();
    }

    private static void deleteTempDataFile(String dataFilePath) {
        File dataFile = new File(dataFilePath);
        dataFile.delete();
    }
}
