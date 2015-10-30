package main.java.pl.lodz.p.ftims.poid;

import main.java.pl.lodz.p.ftims.poid.model.Image;
import main.java.pl.lodz.p.ftims.poid.operations.Operations;
import main.java.pl.lodz.p.ftims.poid.operations.basic.Brightness;
import main.java.pl.lodz.p.ftims.poid.operations.basic.Contrast;
import main.java.pl.lodz.p.ftims.poid.operations.basic.Negative;
import main.java.pl.lodz.p.ftims.poid.operations.filters.MeanFilter;
import main.java.pl.lodz.p.ftims.poid.operations.filters.MedianFilter;
import main.java.pl.lodz.p.ftims.poid.samples.BasicFiltersMasks;
import main.java.pl.lodz.p.ftims.poid.samples.NonLinearFilters;
import main.java.pl.lodz.p.ftims.poid.samples.SampleFiles;
import main.java.pl.lodz.p.ftims.poid.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author alisowsk
 */
public class MainWindow extends JFrame{
    private static final int MAX_IMG_WIDTH = 200;
    private static final int MAX_IMG_HEIGHT = 200;
    private static final Logger LOG = LoggerFactory.getLogger(MainWindow.class);

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
    private JComboBox originalImageSelectComboBox;
    private JFileChooser fileChooser;

    // images section
    private JLabel originalImageIconLabel;
    private JLabel transformedImageIconLabel;
    private JLabel originalImageTextLabel;
    private JLabel transformedImageTextLabel;

    // basic operations section
    private JLabel basicOperationsTextLabel;
    private JCheckBox contrastCheckbox;
    private JCheckBox brightnessCheckbox;
    private JCheckBox negativeCheckBox;
    private JTextField brightnessTextField;
    private JTextField contrastTextField;

    //basic filters section
    private JLabel basicFiltersMaskSizeTextLabel;
    private JLabel basicFiltersTextLabel;
    private JComboBox filterMaskSizeSelectComboBox;
    private JCheckBox meanFilterCheckbox;
    private JCheckBox medianFilterCheckbox;

    //non linear filters section
    private JLabel nonLinearFiltersTextField;
    private JCheckBox nonLinearFiltersCheckbox;
    private JComboBox nonLinearFiltersSelectComboBox;

    // transform button section
    private JButton startTransformButton;

    // logic components
    private Image sourceImage;
    private Image resultImage;
    private Operations operations;

    public MainWindow(){
        super("Image and Sound Processing - TUL, FTiMS 2015/2016");
        getContentPane().setLayout(null);

        initializeGraphicComponents();
        initializeListeners();
        initializeLogicComponents();
        initWindow();
    }

    private void initializeLogicComponents() {
        operations = new Operations();
    }

    private void initializeGraphicComponents() {
        initializeMenuComponents();
        initializePathSectionComponents();
        initializeImagesSection();
        initializeBasicOperationsSection();
        initializeBasicFiltersSection();
        initializeNonLinearFiltersSection();
        initializeTransformButtonSection();
        initializeHelperGrid();
    }

    private void initializeListeners(){
        initializePathSectionListeners();
        initializeTransformButtonListeners();
    }

    private void initializeTransformButtonListeners() {
        startTransformButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                operations.clear();
                if(negativeCheckBox.isSelected()){
                    operations.addOperation(new Negative());
                }
                if(brightnessCheckbox.isSelected()){
                    int brightnessValue = Integer.parseInt(brightnessTextField.getText());
                    operations.addOperation(new Brightness(brightnessValue));
                }
                if(contrastCheckbox.isSelected()){
                    float contrastValue = Float.parseFloat(contrastTextField.getText());
                    operations.addOperation(new Contrast(contrastValue));
                }
                if(meanFilterCheckbox.isSelected()){
                    int maskSize = Integer.parseInt(String.valueOf(BasicFiltersMasks.MASKS.get(filterMaskSizeSelectComboBox.getSelectedItem())));
                    operations.addOperation(new MeanFilter(maskSize));
                }
                if(medianFilterCheckbox.isSelected()){
                    int maskSize = Integer.parseInt(String.valueOf(BasicFiltersMasks.MASKS.get(filterMaskSizeSelectComboBox.getSelectedItem())));
                    operations.addOperation(new MedianFilter(maskSize));
                }
                if(nonLinearFiltersCheckbox.isSelected()){
                    operations.addOperation(NonLinearFilters.FILTERS.get(nonLinearFiltersSelectComboBox.getSelectedItem()));
                }
                resultImage = operations.processImage(sourceImage);
                BufferedImage resultBufferedImage = ImageUtil.convertImageToBufferedImage(resultImage);
                java.awt.Image resultBufferedScaledImage = resultBufferedImage.getScaledInstance(MAX_IMG_WIDTH, MAX_IMG_HEIGHT, java.awt.Image.SCALE_FAST);
                transformedImageIconLabel.setIcon(new ImageIcon(resultBufferedScaledImage));
                try {
                    ImageUtil.saveImageToFile(resultImage);
                } catch (IOException e1) {
                    //TODO ex
                    e1.printStackTrace();
                }
            }
        });
    }

    private void initializeTransformButtonSection() {
        startTransformButton = new JButton("Transform!");
        startTransformButton.setBounds(808, 563, 155, 82);
        getContentPane().add(startTransformButton);
    }

    private void initializePathSectionListeners() {
        originalImageFileChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int returnVal = fileChooser.showOpenDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    originalImagePathTextInput.setText(file.getPath());
                    BufferedImage bufImg = null;
                    try {
                        bufImg = ImageIO.read(file);
                    } catch (Exception ex) {
                        LOG.error("An unexpected error while reading image from file has occurred", ex);
                    }
                    java.awt.Image scaledImage = bufImg.getScaledInstance(200, 200, java.awt.Image.SCALE_FAST);
                    originalImageIconLabel.setIcon(new ImageIcon(scaledImage));
                    try {
                        sourceImage = ImageUtil.readImageFromFile(file);
                    } catch (IOException e1) {
                        //TODO ex
                        e1.printStackTrace();
                    }
                }
            }
        });

        originalImageSelectComboBox.addActionListener (e -> {
            String path = ((SampleFiles) originalImageSelectComboBox.getSelectedItem()).getPath();
            File file = new File(getClass().getClassLoader().getResource(path).getFile());
            originalImagePathTextInput.setText(file.getPath());
            BufferedImage bufImg = null;
            try {
                bufImg = ImageIO.read(file);
            } catch (Exception ex) {
                LOG.error("An unexpected error while reading image from file has occurred", ex);
            }
            java.awt.Image scaledImage = bufImg.getScaledInstance(200, 200, java.awt.Image.SCALE_FAST);
            originalImageIconLabel.setIcon(new ImageIcon(scaledImage));
            try {
                sourceImage = ImageUtil.readImageFromFile(file);
            } catch (IOException e1) {
                //TODO ex
                e1.printStackTrace();
            }
        });
    }

    private void initializeBasicOperationsSection() {
        basicOperationsTextLabel = new JLabel("Basic operations");
        basicOperationsTextLabel.setBounds(34, 302, 200, 50);
        getContentPane().add(basicOperationsTextLabel);

        negativeCheckBox = new JCheckBox("Negative");
        negativeCheckBox.setBounds(32, 348, 133, 24);
        getContentPane().add(negativeCheckBox);

        brightnessCheckbox = new JCheckBox("Brightness");
        brightnessCheckbox.setBounds(34, 384, 133, 24);
        getContentPane().add(brightnessCheckbox);

        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(207, 383, 92, 27);
        getContentPane().add(brightnessTextField);
        brightnessTextField.setColumns(10);

        contrastCheckbox = new JCheckBox("Contrast");
        contrastCheckbox.setBounds(34, 421, 133, 24);
        getContentPane().add(contrastCheckbox);

        contrastTextField = new JTextField();
        contrastTextField.setBounds(207, 421, 92, 27);
        getContentPane().add(contrastTextField);
    }

    private void initializeBasicFiltersSection(){
        basicFiltersTextLabel = new JLabel("Basic filters");
        basicFiltersTextLabel.setBounds(34, 477, 200, 50);
        getContentPane().add(basicFiltersTextLabel);

        basicFiltersMaskSizeTextLabel = new JLabel("Mask size");
        basicFiltersMaskSizeTextLabel.setBounds(34, 589, 138, 30);
        getContentPane().add(basicFiltersMaskSizeTextLabel);

        meanFilterCheckbox = new JCheckBox("Mean filter");
        meanFilterCheckbox.setBounds(34, 523, 148, 24);
        getContentPane().add(meanFilterCheckbox);

        medianFilterCheckbox = new JCheckBox("Median filter");
        medianFilterCheckbox.setBounds(34, 554, 148, 24);
        getContentPane().add(medianFilterCheckbox);

        filterMaskSizeSelectComboBox = new JComboBox(BasicFiltersMasks.MASKS.keySet().toArray());
        filterMaskSizeSelectComboBox.setBounds(207, 591, 92, 27);
        getContentPane().add(filterMaskSizeSelectComboBox);
    }

    private void initializeNonLinearFiltersSection(){
        nonLinearFiltersTextField = new JLabel("Non linear filters");
        nonLinearFiltersTextField.setBounds(365, 477, 200, 50);
        getContentPane().add(nonLinearFiltersTextField);

        nonLinearFiltersCheckbox = new JCheckBox("Operator");
        nonLinearFiltersCheckbox.setBounds(370, 523, 148, 24);
        getContentPane().add(nonLinearFiltersCheckbox);

        nonLinearFiltersSelectComboBox = new JComboBox(NonLinearFilters.FILTERS.keySet().toArray());
        nonLinearFiltersSelectComboBox.setBounds(486, 522, 177, 27);
        getContentPane().add(nonLinearFiltersSelectComboBox);
    }

    private void initializePathSectionComponents() {
        originalImagePathTextLabel = new JLabel("Original image path");
        originalImagePathTextLabel.setBounds(12, 54, 145, 17);
        getContentPane().add(originalImagePathTextLabel);

        transformedImagePathTextLabel = new JLabel("Transformed image path");
        transformedImagePathTextLabel.setBounds(12, 153, 206, 17);
        getContentPane().add(transformedImagePathTextLabel);

        originalImagePathTextInput = new JTextField();
        originalImagePathTextInput.setBounds(12, 83, 491, 27);
        originalImagePathTextInput.setEnabled(false);
        originalImagePathTextInput.setColumns(10);
        getContentPane().add(originalImagePathTextInput);

        transformedImagePathTextInput = new JTextField();
        transformedImagePathTextInput.setColumns(10);
        transformedImagePathTextInput.setBounds(12, 171, 491, 27);
        transformedImagePathTextInput.setEnabled(false);
        getContentPane().add(transformedImagePathTextInput);

        originalImageFileChooserButton = new JButton("Choose image");
        originalImageFileChooserButton.setBounds(358, 42, 145, 29);
        getContentPane().add(originalImageFileChooserButton);

        fileChooser = new JFileChooser();

        originalImageSelectComboBox = new JComboBox(SampleFiles.values());
        originalImageSelectComboBox.setToolTipText("Select input image");
        originalImageSelectComboBox.setBounds(148, 49, 198, 27);
        getContentPane().add(originalImageSelectComboBox);
    }

    private void initializeImagesSection() {
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
        verticalStrut.setBounds(507, 18, 11, 447);
        getContentPane().add(verticalStrut);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        horizontalStrut_1.setBounds(-153, 455, 671, 10);
        getContentPane().add(horizontalStrut_1);
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
