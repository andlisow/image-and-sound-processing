package main.java.pl.lodz.p.ftims.poid.exercise3.samples;

/**
 * @author alisowsk
 */
public enum SampleFiles {
    ART_DIF_80("main/resources/exercise_3/artificial/diff/80Hz.wav"),
    ART_DIF_120("main/resources/exercise_3/artificial/diff/120Hz.wav"),
    ART_DIF_180("main/resources/exercise_3/artificial/diff/180Hz.wav"),
    ART_DIF_270("main/resources/exercise_3/artificial/diff/270Hz.wav"),
    ART_DIF_405("main/resources/exercise_3/artificial/diff/405Hz.wav"),
    ART_DIF_607("main/resources/exercise_3/artificial/diff/607Hz.wav"),
    ART_DIF_911("main/resources/exercise_3/artificial/diff/911Hz.wav"),
    ART_DIF_1366("main/resources/exercise_3/artificial/diff/1366Hz.wav"),

    ART_EASY_100("main/resources/exercise_3/artificial/easy/100Hz.wav"),
    ART_EASY_150("main/resources/exercise_3/artificial/easy/150Hz.wav"),
    ART_EASY_225("main/resources/exercise_3/artificial/easy/225Hz.wav"),
    ART_EASY_337("main/resources/exercise_3/artificial/easy/337Hz.wav"),
    ART_EASY_506("main/resources/exercise_3/artificial/easy/506Hz.wav"),
    ART_EASY_759("main/resources/exercise_3/artificial/easy/759Hz.wav"),
    ART_EASY_1139("main/resources/exercise_3/artificial/easy/1139Hz.wav"),
    ART_EASY_1708("main/resources/exercise_3/artificial/easy/1708Hz.wav"),

    ART_MED_90("main/resources/exercise_3/artificial/med/90Hz.wav"),
    ART_MED_135("main/resources/exercise_3/artificial/med/135Hz.wav"),
    ART_MED_202("main/resources/exercise_3/artificial/med/202Hz.wav"),
    ART_MED_303("main/resources/exercise_3/artificial/med/303Hz.wav"),
    ART_MED_455("main/resources/exercise_3/artificial/med/455Hz.wav"),
    ART_MED_683("main/resources/exercise_3/artificial/med/683Hz.wav"),
    ART_MED_1025("main/resources/exercise_3/artificial/med/1025Hz.wav"),
    ART_MED_1537("main/resources/exercise_3/artificial/med/1537Hz.wav"),

    NAT_FLUTE_276("main/resources/exercise_3/natural/flute/276Hz.wav"),
    NAT_FLUTE_443("main/resources/exercise_3/natural/flute/443Hz.wav"),
    NAT_FLUTE_591("main/resources/exercise_3/natural/flute/591Hz.wav"),
    NAT_FLUTE_887("main/resources/exercise_3/natural/flute/887Hz.wav"),
    NAT_FLUTE_1265("main/resources/exercise_3/natural/flute/1265Hz.wav"),
    NAT_FLUTE_1779("main/resources/exercise_3/natural/flute/1779Hz.wav"),

    NAT_VIOLA_130("main/resources/exercise_3/natural/viola/130Hz.wav"),
    NAT_VIOLA_196("main/resources/exercise_3/natural/viola/196Hz.wav"),
    NAT_VIOLA_247("main/resources/exercise_3/natural/viola/247Hz.wav"),
    NAT_VIOLA_294("main/resources/exercise_3/natural/viola/294Hz.wav"),
    NAT_VIOLA_369("main/resources/exercise_3/natural/viola/369Hz.wav"),
    NAT_VIOLA_440("main/resources/exercise_3/natural/viola/440Hz.wav"),
    NAT_VIOLA_698("main/resources/exercise_3/natural/viola/698Hz.wav"),

    SEQ_DWK_VIOLIN("main/resources/exercise_3/seq/DWK_violin.wav"),
    SEQ_KDF_PIANO("main/resources/exercise_3/seq/KDF_piano.wav");

    private String path;

    SampleFiles(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
