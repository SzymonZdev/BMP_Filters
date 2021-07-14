import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    private static boolean fileLoaded;
    private static Path picture;
    private static int[][][] latestPixelArray;
    private static Picture userPicture;

    public static void main(String[] args) {
        JFrame frame = new JFrame();//creating instance of JFrame
        ImageIcon img = new ImageIcon(".\\Resources\\Icon\\image_icon.png");
        frame.setIconImage(img.getImage());
        frame.setTitle("BMP file conversion");
        frame.setSize(450, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);
        panel.setDoubleBuffered(true);

        JLabel userLabel = new JLabel("Operations:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);


        JLabel pictureName = new JLabel();
        pictureName.setBounds(200, 50, 200, 25);

        JLabel pictureConverted = new JLabel();
        pictureConverted.setBounds(200, 80, 200, 25);

        JLabel pictureDownloaded = new JLabel();
        pictureDownloaded.setBounds(200, 110, 200, 25);

        JButton convertTheImage = new JButton("Convert the image");//creating instance of JButton
        convertTheImage.setBounds(10, 80, 180, 25);//x axis, y axis, width, height
        //adding button
        convertTheImage.addActionListener(e -> {
            try {
                userPicture = new Picture(picture);
                latestPixelArray = Converter.greyScale(userPicture.allPixels);
                pictureConverted.setText("Image converted!");
                panel.add(pictureConverted);
                panel.revalidate();
                panel.repaint();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        JButton yourBmpPicture = new JButton("Choose your BMP picture");//creating instance of JButton
        yourBmpPicture.setBounds(10, 50, 180, 25);//x axis, y axis, width, height
        panel.add(yourBmpPicture);//adding button
        yourBmpPicture.addActionListener(e -> {
            picture = getFile();
            if (fileLoaded && picture != null) {
                pictureName.setText("Image chosen: " + picture.getFileName().toString());
                panel.add(pictureName);
                panel.add(convertTheImage);
            }
            panel.revalidate();
            panel.repaint();
        });

        JButton downloadTheImage = new JButton("Download the image");//creating instance of JButton
        downloadTheImage.setBounds(10, 110, 180, 25);//x axis, y axis, width, height
        panel.add(downloadTheImage);//adding button
        downloadTheImage.addActionListener(e -> {
            saveFile();
            pictureDownloaded.setText("Image downloaded!");
            panel.add(pictureDownloaded);
            panel.add(convertTheImage);
            panel.revalidate();
            panel.repaint();
        });

        JButton exit = new JButton();//creating instance of JButton
        ImageIcon img = new ImageIcon(".\\Resources\\Icon\\exit_icon.png");
        exit.setIcon(img);
        exit.setBounds(350, 180, 60, 25);//x axis, y axis, width, height
        panel.add(exit);//adding button
        exit.addActionListener(e -> System.exit(0));
    }

    private static Path getFile() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP files", "bmp");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new java.io.File(".\\Images"));
        chooser.setDialogTitle("Please select the BMP image");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            fileLoaded = true;
            return Paths.get(chooser.getSelectedFile().getAbsolutePath());
        }
        System.out.println("Error, please try again.");
        // here we have to reset the flow and tell the user something went wrong
        return null;
    }

    private static void saveFile() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("BMP files", "bmp");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new java.io.File(".\\Images\\Converted"));
        fileChooser.setDialogTitle("Choose where to save the converted image");

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            byte[] combinedConverted = userPicture.createFile(latestPixelArray);
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".bmp")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".bmp");
            }
            Path convertedPath = Paths.get(fileToSave.getAbsolutePath());
            try {
                Files.write(convertedPath, combinedConverted);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
