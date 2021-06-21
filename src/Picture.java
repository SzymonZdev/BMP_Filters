import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    public Pixel pixel;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) {
        try {
            Picture testPic = new Picture();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Picture() throws IOException {
        // gets the path of the file for the Files class to operate on
        Path path = Paths.get("C:\\Users\\simon\\Desktop\\Automation\\Java Masterclass\\PNG_Filters\\Images\\stadium.bmp");
        this.path = path;

        // reads the file at path and saves all bytes to an array of bytes
        byte[] file = Files.readAllBytes(path);

        this.allBytes = file;

        // copies and stores the 54 Byte header file
        this.fileHeaderBytes = Arrays.copyOfRange(file, 0, 54);

        // copies and stores the "clean" version - without headers
        this.cleanFileBytes = Arrays.copyOfRange(file, 55, file.length);

        this.header = new Header();
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


    private class Pixel {

        // Define the pixel as a storage vessel for the 3 rgb values
        // Picture is then a collection (resolution) of the pixels
        // We can store here the values of each pixel's corresponding neighbor pixels
        // for easier conversion operations



    }

    // stackoverflow method to convert byte array into hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    // stackoverflow method to create an ArrayList of strings of my pixels
    public static List<String> hexStringArray(String hexString) {
        List<String> strings = new ArrayList<String>();
        int index = 0;
        while (index < hexString.length()) {
            strings.add(hexString.substring(index, Math.min(index + 6,hexString.length())));
            index += 6;
        }

        return strings;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
