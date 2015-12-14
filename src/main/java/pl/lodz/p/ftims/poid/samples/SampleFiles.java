package main.java.pl.lodz.p.ftims.poid.samples;

/**
 * @author alisowsk
 */
public enum SampleFiles {
    BOAT_1 ("main/resources/bit1/boatbw_small.bmp"),
    GIRL_1 ("main/resources/bit1/girlbw_small.bmp"),
    LENA_1 ("main/resources/bit1/lenabw_small.bmp"),
    MANDRIL_1 ("main/resources/bit1/mandrilbw_small.bmp"),

    LENA_8_IMPULSE_1 ("main/resources/bit8/noise/lena_impulse1_small.bmp"),
    LENA_8_IMPULSE_2 ("main/resources/bit8/noise/lena_impulse2_small.bmp"),
    LENA_8_IMPULSE_3 ("main/resources/bit8/noise/lena_impulse3_small.bmp"),
    LENA_8_NORMAL_1 ("main/resources/bit8/noise/lena_normal1_small.bmp"),
    LENA_8_NORMAL_2 ("main/resources/bit8/noise/lena_normal2_small.bmp"),
    LENA_8_NORMAL_3 ("main/resources/bit8/noise/lena_normal3_small.bmp"),
    LENA_8_UNIFORM_1 ("main/resources/bit8/noise/lena_uniform1_small.bmp"),
    LENA_8_UNIFORM_2 ("main/resources/bit8/noise/lena_uniform1_small.bmp"),
    LENA_8_UNIFORM_3 ("main/resources/bit8/noise/lena_uniform3_small.bmp"),

    AERO_8 ("main/resources/bit8/aero_small.bmp"),
    BIRD_8 ("main/resources/bit8/bird_small.bmp"),
    BOAT_8 ("main/resources/bit8/boat_small.bmp"),
    BRIDGE_8 ("main/resources/bit8/bridge_small.bmp"),
    CAMERA_8 ("main/resources/bit8/camera_small.bmp"),
    LENA_8 ("main/resources/bit8/lena_small.bmp"),
    MANDRIL_8 ("main/resources/bit8/mandril_small.bmp"),
    MESSER_8 ("main/resources/bit8/messer_small.bmp"),
    PENTAGON_8 ("main/resources/bit8/pentagon_small.bmp"),

    LENA_24_IMPULSE_1 ("main/resources/bit24/noise/lenac_impulse1_small.bmp"),
    LENA_24_IMPULSE_2 ("main/resources/bit24/noise/lenac_impulse2_small.bmp"),
    LENA_24_IMPULSE_3 ("main/resources/bit24/noise/lenac_impulse3_small.bmp"),
    LENA_24_NORMAL_1 ("main/resources/bit24/noise/lenac_normal1_small.bmp"),
    LENA_24_NORMAL_2 ("main/resources/bit24/noise/lenac_normal2_small.bmp"),
    LENA_24_NORMAL_3 ("main/resources/bit24/noise/lenac_normal3_small.bmp"),
    LENA_24_UNIFORM_1 ("main/resources/bit24/noise/lenac_uniform1_small.bmp"),
    LENA_24_UNIFORM_2 ("main/resources/bit24/noise/lenac_uniform1_small.bmp"),
    LENA_24_UNIFORM_3 ("main/resources/bit24/noise/lenac_uniform3_small.bmp"),

    GIRL_24 ("main/resources/bit24/girlc_small.bmp"),
    LENA_24 ("main/resources/bit24/lenac_small.bmp"),
    MANDRIL_24 ("main/resources/bit24/mandrilc_small.bmp"),

    FOURIER_F5_1 ("main/resources/highpass_filter_with_detection_of_edge_direction/images/edge-detection-1.bmp"),
    FOURIER_F5_2 ("main/resources/highpass_filter_with_detection_of_edge_direction/images/edge-detection-2.bmp"),
    FOURIER_F5_3 ("main/resources/highpass_filter_with_detection_of_edge_direction/images/edge-detection-3.bmp"),
    ;

    private String path;

    SampleFiles(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
