package main.java.pl.lodz.p.ftims.poid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author alisowsk
 */
public class MainWindow extends JFrame{
    // menu section
    private JMenuBar mainMenuBar;
    private JMenu helpMenuSection;
    private JMenu fileMenuSection;

    // paths section
    private JLabel originalImagePathTextLabel;
    private JLabel transformedImagePathTextLabel;
    private JTextField originalImagePathTextInput;
    private JTextField transformedImagePathTextInput;
    private JButton originalImageFileChooserButton;
    private JFileChooser fileChooser;

    // icons section
    private JLabel originalImageIconLabel;
    private JLabel transformedImageIconLabel;
    private JLabel originalImageTextLabel;
    private JLabel transformedImageTextLabel;

    // operations section
    private JButton startTransformButton;

    public MainWindow(){
        super("Image and Sound Processing - TUL, FTiMS 2015/2016");
        getContentPane().setLayout(null);

        initializeComponents();
        initializeListeners();
        initWindow();
    }

    private void initializeListeners() {
        originalImageFileChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int returnVal = fileChooser.showOpenDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    originalImagePathTextInput.setText(file.getName());
                    try {
                        BufferedImage bufImg = ImageIO.read(file);
                        Image scaledImage = bufImg.getScaledInstance(200, 200, Image.SCALE_FAST);
                        originalImageIconLabel.setIcon(new ImageIcon(scaledImage));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {

                }
            }
        });
    }

    private void initializeComponents() {
        initializeMenuComponents();
        initializePathsSectionComponents();
        initializeIconsSection();
        initializeOperationsSection();
        initializeHelperGrid();
    }

    private void initializeOperationsSection() {
        startTransformButton = new JButton("Transform!");
        startTransformButton.setBounds(808, 563, 155, 82);
        getContentPane().add(startTransformButton);
    }

    private void initializePathsSectionComponents() {
        originalImagePathTextLabel = new JLabel("Original image path");
        originalImagePathTextLabel.setBounds(34, 54, 187, 17);
        getContentPane().add(originalImagePathTextLabel);

        transformedImagePathTextLabel = new JLabel("Transformed image path");
        transformedImagePathTextLabel.setBounds(34, 144, 206, 17);
        getContentPane().add(transformedImagePathTextLabel);

        originalImagePathTextInput = new JTextField();
        originalImagePathTextInput.setBounds(34, 83, 413, 27);
        originalImagePathTextInput.setEnabled(false);
        originalImagePathTextInput.setColumns(10);
        getContentPane().add(originalImagePathTextInput);

        transformedImagePathTextInput = new JTextField();
        transformedImagePathTextInput.setColumns(10);
        transformedImagePathTextInput.setBounds(34, 174, 413, 27);
        transformedImagePathTextInput.setEnabled(false);
        getContentPane().add(transformedImagePathTextInput);

        originalImageFileChooserButton = new JButton("Choose image");
        originalImageFileChooserButton.setBounds(302, 48, 145, 29);
        getContentPane().add(originalImageFileChooserButton);

        fileChooser = new JFileChooser();
    }

    private void initializeIconsSection() {
        originalImageIconLabel = new JLabel("");
        originalImageIconLabel.setBounds(530, 80, 200, 200);
        originalImageIconLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(originalImageIconLabel);

        transformedImageIconLabel = new JLabel("");
        transformedImageIconLabel.setBounds(760, 80, 200, 200);
        transformedImageIconLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(transformedImageIconLabel);

        originalImageTextLabel = new JLabel("Original image");
        originalImageTextLabel.setBounds(530, 50, 200, 20);
        getContentPane().add(originalImageTextLabel);

        transformedImageTextLabel = new JLabel("Transformed image");
        transformedImageTextLabel.setBounds(760, 50, 200, 20);
        getContentPane().add(transformedImageTextLabel);
    }

    private void initializeHelperGrid() {
        Component horizontalStrut = Box.createHorizontalStrut(20);
        horizontalStrut.setBounds(-5, 277, 1003, 10);
        getContentPane().add(horizontalStrut);

        Component verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setBounds(482, 20, 11, 262);
        getContentPane().add(verticalStrut);
    }

    private void initializeMenuComponents() {
        mainMenuBar = new JMenuBar();
        mainMenuBar.setBounds(0, 0, 1000, 30);
        getContentPane().add(mainMenuBar);

        fileMenuSection = new JMenu("File");
        mainMenuBar.add(fileMenuSection);

        helpMenuSection = new JMenu("Help");
        mainMenuBar.add(helpMenuSection);
    }

    private void initWindow() {
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1000,700);
    }
}
