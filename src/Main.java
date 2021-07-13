import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        Picture picture = new Picture();
        int[][][] convertedPixels = Converter.greyScale(picture.allPixels);
        Integer[] singleDimension = Picture.convertToOneDimension(convertedPixels);
        byte[] array = Picture.convertToByteArray(singleDimension);
        byte[] combinedConverted = picture.createConvertedFile(array);

        Path convertedPath = Paths.get("Images\\converted\\grey.bmp");
        Files.write(convertedPath, combinedConverted);
    }
}
