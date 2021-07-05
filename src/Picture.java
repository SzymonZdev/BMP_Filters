import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

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
        //TODO lepiej byłoby stworzyć konstruktor z parametrem, do którego się przekaże path z Main
        this.path = Paths.get("Images/courtyard.bmp");

        // reads the file at path and saves all bytes to an array of bytes
        this.allBytes = Files.readAllBytes(path);

        // copies and stores the 54 Byte header file
        this.fileHeaderBytes = Arrays.copyOfRange(allBytes, 0, 54);

        // copies and stores the "clean" version - without headers
        this.cleanFileBytes = Arrays.copyOfRange(allBytes, 55, allBytes.length);

        // initialise the header object
        this.header = new Header();

        // generate the multidimensional array of integer values of the pixels
        this.allPixels = generatePixelArray(this.cleanFileBytes);
    }

    //TODO tutaj mi jakoś to nie pasuje z tym headerem
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

        // Stackoverflow method to little-endian convert the byte array into an integer value
        //TODO to powinno być w innej klasie, jakimś extensionMethod, bo nie jest to atrybut nagłowka
        private int getIntValue(byte[] bytes) {
            int result = 0;
            // loop through the values
            for (int i = 0; i < bytes.length; i++) {
                // shifting the bits depending on where in the byte array they are
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
    //TODO to tak samo do exstensionMethod
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
    //TODO to tak samo
    public byte[] createConvertedFile(byte[] convertedBytes) {
        byte[] allByteArray = new byte[this.fileHeaderBytes.length + convertedBytes.length];

        ByteBuffer buff = ByteBuffer.wrap(allByteArray);
        buff.put(this.fileHeaderBytes);
        buff.put(convertedBytes);

        return buff.array();
    }
    //TODO ogólnie wszystkie takie metody powinny być w jakimś extensionMethod pogrupowane, możesz z tego zrobić jakąś bibliotekę, która może być zarządzana oddzielnie
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

    // Separated one method into two distinct problems
    // First compress the multi dimensional array into a simple array
    public static Integer[] convertToOneDimension(int[][][] pixelArray) {
        ArrayList<Integer> resultList = new ArrayList<>();
        //TODO to akurat była sugestia IntelliJ
        for (int[][] ints : pixelArray) {
            for (int j = 0; j < pixelArray[0].length; j++) {
                for (int k = 0; k < pixelArray[0][0].length; k++) {
                    resultList.add(ints[j][k]);
                }
            }
        }
        return resultList.toArray(new Integer[0]);
    }


    // Then convert that single array of integers to an array of bytes
    public static byte[] convertToByteArray(Integer[] pixelArray) {
        byte[] convertedArray = new byte[pixelArray.length];
        int index = 0;

        for (Integer value : pixelArray) {
            convertedArray[index] = value.byteValue();
            index++;
        }

        ByteBuffer bb = ByteBuffer.wrap(convertedArray);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        convertedArray = bb.array();

        return convertedArray;
    }
}
