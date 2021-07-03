import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Picture {
    public Path path;
    public byte[] allBytes;
    public byte[] fileHeaderBytes;
    public byte[] cleanFileBytes;
    public Header header;
    public int[][][] allPixels;

    public static void main(String[] args) {
    }

    public Picture() throws IOException {
        // gets the path of the file for the Files class to operate on
        Path path = Paths.get("C:\\Users\\Anna\\IdeaProjects\\PNG_Filters\\Images\\stadium.bmp");
        this.path = path;

        // reads the file at path and saves all bytes to an array of bytes
        byte[] file = Files.readAllBytes(path);

        this.allBytes = file;

        // copies and stores the 54 Byte header file
        this.fileHeaderBytes = Arrays.copyOfRange(file, 0, 54);

        // copies and stores the "clean" version - without headers
        this.cleanFileBytes = Arrays.copyOfRange(file, 55, file.length);

        // initialise the header object
        this.header = new Header();

        // generate the multidimensional array of integer values of the pixels
        this.allPixels = generatePixelArray(this.cleanFileBytes);
    }

    private class Header {

        public byte[] header = fileHeaderBytes;

        public byte[] idField;
        public byte[] sizeOfFile;
        public byte[] unused;
        public byte[] offSetForPixels;
        public byte[] DIBHeader;
        public byte[] width;
        public byte[] height;
        public byte[] colorPlanes;
        public byte[] bitsPerPixel;
        public byte[] arrayCompression;
        public byte[] rawSize;
        public byte[] printResolution;
        public byte[] numberColors;
        public byte[] importantColors;

        public int widthValue;
        public int heightValue;
        public int bitsPerPixelValue;

        public Header() {
            idField = Arrays.copyOfRange(header, 0, 2);
            sizeOfFile = Arrays.copyOfRange(header, 2, 6);
            unused = Arrays.copyOfRange(header, 6, 10);
            offSetForPixels = Arrays.copyOfRange(header, 10, 14);
            DIBHeader = Arrays.copyOfRange(header, 14, 18);
            width = Arrays.copyOfRange(header, 18, 22);
            height = Arrays.copyOfRange(header, 22, 26);
            colorPlanes = Arrays.copyOfRange(header, 26, 28);
            bitsPerPixel = Arrays.copyOfRange(header, 28, 30);
            arrayCompression = Arrays.copyOfRange(header, 30, 34);
            rawSize = Arrays.copyOfRange(header, 34, 38);
            printResolution = Arrays.copyOfRange(header, 38, 46);
            numberColors = Arrays.copyOfRange(header, 46, 50);
            importantColors = Arrays.copyOfRange(header, 50, 54);

            widthValue = getIntValue(this.width);
            heightValue = getIntValue(this.height);
            bitsPerPixelValue = getIntValue(this.bitsPerPixel);
        }

        private int getIntValue(byte[] bytes) {
            int result = 0;
            for (int i = 0; i < bytes.length; i++) {
                if (i == 0) {
                    result |= bytes[i] << (0);
                } else {
                    result |= bytes[i] << (8 * i);
                }
            }
            return Math.abs(result);
        }
    }

    // takes the "clean" bytes from the bmp file and creates a three-dimensional array
    //

    public int[][][] generatePixelArray(byte[] allBytes) {
        int[][][] pixelArray = new int[this.header.heightValue][this.header.widthValue][3];
        int byteIndex = 0;

        // Looping through each row of the Picture
        for (int i = 0; i < this.header.heightValue; i++) {
            // Looping through each column of the picture
            for (int j = 0; j < this.header.widthValue; j++) {
                // Looping through each pixel color value(GRB - green, red, blue)
                for (int k = 0; k < 3; k++) {
                    pixelArray[i][j][k] = getIntValue(Arrays.copyOfRange(allBytes, byteIndex, byteIndex+1));
                    byteIndex++;
                }
            }
        }

        return pixelArray;
    }

    private int getIntValue(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (i == 0) {
                result |= bytes[i] << (0);
            } else {
                result |= bytes[i] << (8 * i);
            }
        }
        return Math.abs(result);
    }

    // Need a method here to take the multi-dimensional array and create just a simple array of bytes
    public static byte[] convertToByteArray(int[][][] pixelArray) {
        if (pixelArray == null)
            return null;
        int[] singleDimension = new int[pixelArray.length];
        int index = 0;
        for (int[][] ints : pixelArray) {
            for (int[] anInt : ints) {
                for (int k = 0; k < 3; k++) {
                    singleDimension[index] = anInt[k];
                }
            }
        }

        byte[] result = new byte[singleDimension.length];
        int indexToo = 0;
        for (int colorInt : singleDimension) {
            result[indexToo] = (byte)colorInt;
        }

        return result;
    }
}
