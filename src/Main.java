import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
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

//        JLabel userLabel = new JLabel("Operations:");
//        userLabel.setBounds(10, 20, 80, 25);
//        panel.add(userLabel);


        JLabel pictureName = new JLabel();
        pictureName.setBounds(20, 50, 200, 25);

        JLabel pictureConverted = new JLabel();
        pictureConverted.setBounds(210, 80, 200, 25);
        pictureConverted.setForeground(Color.GREEN);

        JLabel pictureDownloaded = new JLabel();
        pictureDownloaded.setBounds(200, 110, 200, 25);

        final String[] transformOptions = new String[]{"Grey", "Reflect-Y", "Reflect-X", "Negative"};
        JComboBox<String> transformOptionsCombo = new JComboBox<>(transformOptions);
        transformOptionsCombo.setBounds(10, 80, 90, 25);

        JButton downloadTheConvertedImage = new JButton("Download the image");//creating instance of JButton
        downloadTheConvertedImage.setBounds(10, 110, 180, 25);//x axis, y axis, width, height
        downloadTheConvertedImage.addActionListener(e -> {
            saveFile();
            pictureDownloaded.setText("Image downloaded!");
            panel.add(pictureDownloaded);
            panel.revalidate();
            panel.repaint();
        });

        JButton convertTheImage = new JButton("Convert!");//creating instance of JButton
        convertTheImage.setBounds(100, 80, 100, 25);//x axis, y axis, width, height
        convertTheImage.addActionListener(e -> {
            try {
                userPicture = new Picture(picture);
                latestPixelArray = callConvert(latestPixelArray, String.valueOf(transformOptionsCombo.getSelectedItem()));
                pictureConverted.setText("Image converted!");
                panel.add(pictureConverted);
                panel.add(downloadTheConvertedImage);
                panel.revalidate();
                panel.repaint();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JButton uploadPicture = new JButton("Choose your BMP picture");//creating instance of JButton
        uploadPicture.setBounds(10, 20, 180, 25);//x axis, y axis, width, height
        panel.add(uploadPicture);//adding button
        uploadPicture.addActionListener(e -> {
            picture = getFile();
            if (fileLoaded && picture != null) {
                pictureName.setText("Image chosen: " + picture.getFileName().toString());
                panel.add(pictureName);
                panel.add(transformOptionsCombo);
                panel.add(convertTheImage);
            }
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

    private static int[][][] callConvert(int[][][] latestPixelArray, String optionChosen) {
        switch (optionChosen) {
            case "Grey":
                return Converter.greyScale(userPicture.allPixels);
            case "Reflect-Y":
                return Converter.reflectionY(userPicture.allPixels);
            case "Reflect-X":
                return Converter.reflectionX(userPicture.allPixels);
            case "Negative":
                return Converter.negative(userPicture.allPixels);
            default:
                System.out.println("Something went wrong while converting!");
                return null;
        }
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
