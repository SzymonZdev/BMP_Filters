import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Picture {
    public Path path;
    public byte[] allBytes;
    public byte[] fileHeaderBytes;
    public byte[] cleanFileBytes;

    public Picture() throws IOException {
        // gets the path of the file for the Files class to operate on
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide the absolute path of the JPG you wish to convert:");
        String pathOfFile = scanner.nextLine();
        Path path = Paths.get(pathOfFile);
        this.path = path;

        // reads the file at path and saves all bytes to an array of bytes
        byte[] file = Files.readAllBytes(path);
        this.allBytes = file;

        // copies and stores the 14 Byte header file
        this.fileHeaderBytes = Arrays.copyOfRange(file, 0, 54);

        // copies and stores the 40 Byte second header file
        // byte[] bitmapInfoHeader = Arrays.copyOfRange(file, 15, 54);

        // copies and stores the "clean" version - without headers
        this.cleanFileBytes = Arrays.copyOfRange(file, 55, file.length);
    }
}
