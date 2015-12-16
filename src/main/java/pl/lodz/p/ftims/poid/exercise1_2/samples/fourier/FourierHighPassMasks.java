package main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.fourier;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alisowsk
 */
public class FourierHighPassMasks {
    public static final Map<String, Image> MASKS = new LinkedHashMap<>();
    static{
        MASKS.put("MASK 1", readMask("/home/andrzej/Pulpit/POID/SRC/image-and-sound-processing/src/main/resources/exercise1_2/highpass_filter_with_detection_of_edge_direction/masks/F5mask1.bmp"));
        MASKS.put("MASK 2", readMask("/home/andrzej/Pulpit/POID/SRC/image-and-sound-processing/src/main/resources/exercise1_2/highpass_filter_with_detection_of_edge_direction/masks/F5mask2.bmp"));
    }

    private static Image readMask(String path) {
        try {
            return ImageUtil.readImageFromFile(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            //TODO throw ex
        }
        return null;
    }

    private FourierHighPassMasks(){

    }
}
