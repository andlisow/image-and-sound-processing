package main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.nonlinear;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Pixel.RgbColor;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.AbstractFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageConstants;

/**
 * @author alisowsk
 */
public class KirshOperator extends AbstractFilter {
    public KirshOperator() {
    }

    @Override
    protected boolean checkIfNotBorderValue(Image img, int x, int y) {
        return x == 0 || y == 0 || x == img.getWidth()-1 || y == img.getHeight()-1;
    }

    @Override
    protected int processSingleColor(Image img, int x, int y, RgbColor c) {
        int[] a = calculateA(img,x,y,c);
        int max=0;
        for(int i=0; i<8; i++){
            int temp = Math.abs(5*calculateK1(a,i) - 3*calculateK2(a,i));
            if(temp > max){
                max = temp;
            }
        }
        int result = Math.max(1, max);
        if(result > ImageConstants.MAX_PIXEL_VALUE){
            return ImageConstants.MAX_PIXEL_VALUE;
        }
        return result;
    }

    private int calculateK1(int[] a, int i) {
        int sum=0;
        for(int k=0; k<3; k++){
            sum+=a[Math.floorMod(i+k, 8)];
        }
        return sum;
    }

    private int calculateK2(int[] a, int i) {
        int sum=0;
        for(int k=3; k<8; k++){
            sum+=a[Math.floorMod(i+k, 8)];
        }
        return sum;
    }

    private int[] calculateA(Image img, int x, int y, RgbColor c) {
        return new int[]{
                img.getPixel(x - 1, y - 1).getColor(c),
                img.getPixel(x, y - 1).getColor(c),
                img.getPixel(x + 1, y - 1).getColor(c),
                img.getPixel(x + 1, y).getColor(c),
                img.getPixel(x + 1, y + 1).getColor(c),
                img.getPixel(x, y + 1).getColor(c),
                img.getPixel(x - 1, y + 1).getColor(c),
                img.getPixel(x - 1, y).getColor(c)
        };
    }
}
