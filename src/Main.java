import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws IOException {

        Picture picture = new Picture();

        List<String> pixels = hexStringArray(bytesToHex(picture.cleanFileBytes));
        pixels = Converter.greyScale(pixels);

        StringBuilder greyPixels = new StringBuilder();
        for (String s : pixels)
        {
            greyPixels.append(s);
        }

       byte [] convertedPixels = hexStringToByteArray(greyPixels.toString());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write(picture.fileHeaderBytes );
        // outputStream.write( bitmapInfoHeader );
        outputStream.write( convertedPixels );

        byte [] finalFile = outputStream.toByteArray( );

        Path convertedPath = Paths.get("C:\\Users\\simon\\Pictures\\grey.bmp");
        Files.write(convertedPath, finalFile);


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
