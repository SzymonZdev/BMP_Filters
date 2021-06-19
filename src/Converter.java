import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<String> greyScale(List<String> pixels) {
        List<String> greyPixels = new ArrayList<>();
        for (String pixel : pixels) {
            int average;
            if(pixel.length() < 6) {
                average = 0;
            } else {
                String color1 = pixel.substring(0, 2);
                String color2 = pixel.substring(2, 4);
                String color3 = pixel.substring(4, 6);
                average = (Integer.parseInt(color1, 16) + Integer.parseInt(color2, 16) + Integer.parseInt(color3, 16)) / 3;
            }
            String hexAverage = "";
            if(average <= 15) {
                hexAverage = "0" + Integer.toHexString(average) + "0" + Integer.toHexString(average) + "0" + Integer.toHexString(average);
            } else {
                hexAverage = Integer.toHexString(average) + Integer.toHexString(average) + Integer.toHexString(average);
            }
            greyPixels.add(hexAverage);
        }
        return greyPixels;
    }
}
