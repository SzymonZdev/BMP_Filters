public class Converter {

    public static int[][][] greyScale(int[][][] pixelArray) {
        // Returns a multi-dimensional array of pixels that have been converted to a greyscale picture
        int[][][] greyArray = pixelArray.clone();
        // Loop through each dimension
        // Looping through each row of the Picture
        for (int i = 0; i < pixelArray.length; i++) {
            // Looping through each column of the picture
            for (int j = 0; j < pixelArray[i].length; j++) {
                int averageNum = 0;
                averageNum += pixelArray[i][j][0];
                averageNum += pixelArray[i][j][1];
                averageNum += pixelArray[i][j][2];
                averageNum = averageNum/3;
                // Looping through each pixel color value(GRB - green, red, blue)
                for (int k = 0; k < 3; k++) {
                    greyArray[i][j][k] = averageNum;
                }
            }
        }
        return greyArray;
    }
}
