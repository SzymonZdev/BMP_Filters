import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        Picture picture = new Picture();
        System.out.println("The length of cleanFileBytes is " + picture.cleanFileBytes.length);
        //TODO ta lenght może być jakoś wyliczana wewnątrz klasy Picture i tylko zwracana, bo tutaj się duplikuje kod. Niby raczej to sie nie zmieni, ale lepiej, zeby to liczenie było wywaołne tylko raz gdzieś wewnątrz klasy
        System.out.println("The length of allPixels is " + picture.allPixels.length * picture.allPixels[0].length * 3);
        int[][][] convertedPixels = Converter.greyScale(picture.allPixels);
        System.out.println("The length of grey convertedPixels is " + convertedPixels.length * convertedPixels[0].length * 3);

        Integer[] singleDimension = Picture.convertToOneDimension(convertedPixels);
        System.out.println("The length of the singleDimension is " + singleDimension.length);

        byte[] array = Picture.convertToByteArray(singleDimension);
        System.out.println("The length of the converted byte array is " + array.length);

        byte[] combinedConverted = picture.createConvertedFile(array);
        System.out.println("The length of the combined, final byte array is " + combinedConverted.length);



         Path convertedPath = Paths.get("Images\\converted\\grey.bmp");
         Files.write(convertedPath, combinedConverted);
    }
}
