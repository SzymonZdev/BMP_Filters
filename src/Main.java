import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        Picture picture = new Picture();
        int[][][] convertedPixels = Converter.greyScale(picture.allPixels);
        System.out.println(convertedPixels.length * convertedPixels[0].length);

        Integer[] singleDimension = Picture.convertToOneDimension(convertedPixels);
        System.out.println(singleDimension.length);

        byte[] array = Picture.convertToByteArray(singleDimension);
        System.out.println(array.length);

        byte[] combinedConverted = picture.createConvertedFile(array);
        System.out.println(combinedConverted.length);

        Path convertedPath = Paths.get("Images\\converted\\grey.bmp");
        Files.write(convertedPath, combinedConverted);
    }
}
