public class Converter {

    public static int[][][] greyScale(int[][][] pixelArray) {
        // Returns a multi-dimensional array of pixels that have been converted to a greyscale picture
        int[][][] greyArray = new int[pixelArray.length][pixelArray[0].length][pixelArray[0][0].length];
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

    public static int[][][] reflectionY(int[][][] pixelArray) {
        // Initialize the new array to store the transformed values
        int[][][] reflectedArray = new int[pixelArray.length][pixelArray[0].length][pixelArray[0][0].length];
        // Loop through each dimension
        // Looping through each row of the Picture
        for (int i = 0; i < pixelArray.length; i++) {
                // Looping through each column of the picture, changing the reflected values to be at the other end of the row
                for (int j = 0; j < pixelArray[i].length; j++) {
                        reflectedArray[i][reflectedArray[0].length-1-j] = pixelArray[i][j];
                    }
        }
        return reflectedArray;
    }

    public static int[][][] reflectionX(int[][][] pixelArray) {
        // Initialize the new array to store the transformed values
        int[][][] reflectedArray = new int[pixelArray.length][pixelArray[0].length][pixelArray[0][0].length];
        // Loop through each dimension
        // Looping through each row of the Picture
        for (int i = 0; i < pixelArray.length; i++) {
                // Looping through each row of the picture, changing the reflected values to be at the other end of the column
            reflectedArray[reflectedArray.length-1-i] = pixelArray[i];
        }
        return reflectedArray;
    }

    // rotate?

    // negative colors?

    // lose focus?

    // others?
}
