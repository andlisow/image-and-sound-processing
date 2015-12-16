package main.java.pl.lodz.p.ftims.poid.exercise3.samples;

/**
 * @author alisowsk
 */
public enum SampleFiles {
    LIN ("main/resources/exercise_3/lin.wav"),
    LOG ("main/resources/exercise_3/log.wav");

    private String path;

    SampleFiles(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
