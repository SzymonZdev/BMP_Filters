import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        Picture picture = new Picture();
        int[][][] convertedPixels = Converter.greyScale(picture.allPixels);

        byte[] array = Picture.convertToByteArray(convertedPixels);

        byte[] allByteArray = new byte[picture.fileHeaderBytes.length + array.length];

        ByteBuffer buff = ByteBuffer.wrap(allByteArray);
        buff.put(picture.fileHeaderBytes);
        buff.put(array);

        byte[] combined = buff.array();

        Path convertedPath = Paths.get("Images\\converted\\grey.bmp");
        Files.write(convertedPath, combined);
    }
}
