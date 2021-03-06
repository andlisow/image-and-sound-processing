package main.java.pl.lodz.p.ftims.poid.exercise1_2;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Histogram;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.model.Image;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Operations;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.Transformable;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.basic.Brightness;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.basic.Contrast;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.basic.Negative;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.basic.MeanFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.basic.MedianFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.linear.LinearFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.nonlinear.RosenfeldOperator;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.FourierTransform;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters.FourierFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.filters.HighPassDetectionEdgeDirectionFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.fourier.segmentation.RegionSegmentation;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.histogram.AbstractFinalProbDensFunction;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.HistogramModification;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.SampleFiles;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.filters.BasicFiltersMasks;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.filters.linear.LinearFilters;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.filters.nonlinear.NonLinearFilters;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.fourier.FourierFilters;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.fourier.FourierHighPassMasks;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.segmentation.Segmentation;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.GnuplotUtil;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.HistogramUtil;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.utils.ImageUtil;
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
import java.util.List;

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
//    private JLabel transformedImagePathTextLabel; // TODO implement
    private JTextField originalImagePathTextInput;
//    private JTextField transformedImagePathTextInput; // TODO implement
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

    //linear filters section
    private JLabel linearFiltersTextField;
    private JCheckBox linearFiltersCheckbox;
    private JComboBox linearFiltersSelectComboBox;

    //non linear filters section
    private JLabel nonLinearFiltersTextField;
    private JCheckBox nonLinearFiltersCheckbox;
    private JComboBox nonLinearFiltersSelectComboBox;
    private JTextField rosenfeldOperatorTextField;
    private JLabel rosenfeldOperatorLabel;

    //histogram operations section
    private JLabel histogramOperationsTextLabel;
    private JCheckBox histogramOperationsCheckBox;
    private JComboBox histogramOperationsSelectComboBox;
    private JTextField gMinTextField;
    private JTextField gMaxTextField;
    private JLabel gMinTextLabel;
    private JLabel gMaxTextLabel;

    // transform button section
    private JButton startTransformButton;

    // transform in frequency domain section
    private JTextField freqDomMinValTextInput;
    private JTextField freqDomMaxValTextInput;
    private JCheckBox freqDomCheckbox;
    private JCheckBox freqDomFilterCheckBox;
    private JComboBox freqDomFilterSelectComboBox;
    private JComboBox freqDomHighPassFilterMaskComboBox;
    private JLabel freqDomHighPassFilterMaskTextLabel;
    private JLabel freqDomMinValTextLabel;
    private JLabel freqDomMaxValTextLabel;

    private JCheckBox timeDomHannFilterCheckBox;

    // segmentation section
    private JCheckBox segmentationCheckbox;
    private JComboBox segmentationComboBox;
    private JTextField segmentationThresholdTexInput;
    private JTextField segmentationMinPixForRegionTextInput;
    private JLabel segmentationMinPixForRegionTextLabel;
    private JLabel segmentationThresholdTextLabel;

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
        initializeLinearFilterSection();
        initializeNonLinearFiltersSection();
        initializeHistogramOperationsSection();
        initializeTransformButtonSection();
        initializeFiltrationFrequencyDomainSection();
        initializeSegmentationSection();
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
                if (negativeCheckBox.isSelected()) {
                    operations.addOperation(new Negative());
                }
                if (brightnessCheckbox.isSelected()) {
                    int brightnessValue = Integer.parseInt(brightnessTextField.getText());
                    operations.addOperation(new Brightness(brightnessValue));
                }
                if (contrastCheckbox.isSelected()) {
                    float contrastValue = Float.parseFloat(contrastTextField.getText());
                    operations.addOperation(new Contrast(contrastValue));
                }
                if (meanFilterCheckbox.isSelected()) {
                    int maskSize = Integer.parseInt(String.valueOf(BasicFiltersMasks.MASKS.get(filterMaskSizeSelectComboBox.getSelectedItem())));
                    operations.addOperation(new MeanFilter(maskSize));
                }
                if (medianFilterCheckbox.isSelected()) {
                    int maskSize = Integer.parseInt(String.valueOf(BasicFiltersMasks.MASKS.get(filterMaskSizeSelectComboBox.getSelectedItem())));
                    operations.addOperation(new MedianFilter(maskSize));
                }
                if (linearFiltersCheckbox.isSelected()) {
                    for (String linearFilter : LinearFilters.FILTERS.keySet()) {
                        if (linearFilter.equals(linearFiltersSelectComboBox.getSelectedItem())) {
                            operations.addOperation(new LinearFilter(LinearFilters.FILTERS.get(linearFilter)));
                        }
                    }
                }
                if (nonLinearFiltersCheckbox.isSelected()) {
                    Transformable nonLinearFilter = NonLinearFilters.FILTERS.get(nonLinearFiltersSelectComboBox.getSelectedItem());
                    if (nonLinearFilter instanceof RosenfeldOperator) {
                        RosenfeldOperator operator = (RosenfeldOperator) nonLinearFilter;
                        operator.setrCoefficient(Integer.parseInt(rosenfeldOperatorTextField.getText()));
                    }
                    operations.addOperation(nonLinearFilter);
                }
                if (histogramOperationsCheckBox.isSelected()) {
                    List<Histogram> histograms = HistogramUtil.prepareHistograms(sourceImage);
                    GnuplotUtil.saveHistograms(histograms);
                    for (String histogramModification : HistogramModification.VARIANTS.keySet()) {
                        if (histogramModification.equals(histogramOperationsSelectComboBox.getSelectedItem())) {
                            AbstractFinalProbDensFunction histogramOperation = HistogramModification.VARIANTS.get(histogramModification);
                            histogramOperation.setHistograms(histograms);//TODO
                            histogramOperation.setgMin(Integer.parseInt(gMinTextField.getText()));
                            histogramOperation.setgMax(Integer.parseInt(gMaxTextField.getText()));
                            operations.addOperation(histogramOperation);
                        }
                    }
                }
                if (freqDomCheckbox.isSelected()){
                    if(freqDomFilterCheckBox.isSelected()){
                        for (String fourierFilter : FourierFilters.FILTERS.keySet()) {
                            if (fourierFilter.equals(freqDomFilterSelectComboBox.getSelectedItem())) {
                                FourierFilter filter = FourierFilters.FILTERS.get(fourierFilter);
                                int minOrK=0;
                                int maxOrL=0;
                                if(freqDomMinValTextInput.getText() != null && !freqDomMinValTextInput.getText().isEmpty()){
                                    minOrK = Integer.parseInt(freqDomMinValTextInput.getText());
                                }
                                if(freqDomMaxValTextInput.getText() != null && !freqDomMaxValTextInput.getText().isEmpty()){
                                    maxOrL = Integer.parseInt(freqDomMaxValTextInput.getText());
                                }
                                if(filter instanceof HighPassDetectionEdgeDirectionFilter){
                                    Image mask = null;
                                    for (String maskName : FourierHighPassMasks.MASKS.keySet()) {
                                        if (maskName.equals(freqDomHighPassFilterMaskComboBox.getSelectedItem())) {
                                            mask = FourierHighPassMasks.MASKS.get(maskName);
                                        }
                                    }
                                    ((HighPassDetectionEdgeDirectionFilter) filter).setMask(mask);
                                }
                                filter.setMinOrK(minOrK);
                                filter.setMaxOrL(maxOrL);
                                FourierTransform fourierTransform = new FourierTransform(filter);

                                if(timeDomHannFilterCheckBox.isSelected()){
                                    fourierTransform.setApplyHannWindow(true);
                                }

                                operations.addOperation(fourierTransform);
                            }
                        }
                    } else {
                        FourierTransform fourierTransform = new FourierTransform();

                        if(timeDomHannFilterCheckBox.isSelected()){
                            fourierTransform.setApplyHannWindow(true);
                        }
                        operations.addOperation(fourierTransform);
                    }
                }
                if(segmentationCheckbox.isSelected()){
                    for (String segmentation : Segmentation.SEGMENTATIONS.keySet()) {
                        if (segmentation.equals(segmentationComboBox.getSelectedItem())) {
                            RegionSegmentation segm = Segmentation.SEGMENTATIONS.get(segmentation);
                            segm.setThreshold(Integer.parseInt(segmentationThresholdTexInput.getText()));
                            segm.setMinimumPixelsForRegion(Integer.parseInt(segmentationMinPixForRegionTextInput.getText()));
                            operations.addOperation(segm);
                        }
                    }
                }

                resultImage = operations.processImage(sourceImage);
                BufferedImage resultBufferedImage = ImageUtil.convertImageToBufferedImage(resultImage);
                java.awt.Image resultBufferedScaledImage = resultBufferedImage.getScaledInstance(MAX_IMG_WIDTH, MAX_IMG_HEIGHT, java.awt.Image.SCALE_FAST);
                transformedImageIconLabel.setIcon(new ImageIcon(resultBufferedScaledImage));
                try {
                    ImageUtil.saveImageToFile(resultImage);
                } catch (IOException e1) {
                    //TODO ex
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

        originalImageSelectComboBox.addActionListener(e -> {
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

    private void initializeLinearFilterSection(){
        linearFiltersTextField = new JLabel("Linear filters");
        linearFiltersTextField.setBounds(358, 302, 200, 50);
        getContentPane().add(linearFiltersTextField);

        linearFiltersCheckbox = new JCheckBox("Operator");
        linearFiltersCheckbox.setBounds(370, 348, 148, 24);
        getContentPane().add(linearFiltersCheckbox);

        linearFiltersSelectComboBox = new JComboBox(LinearFilters.FILTERS.keySet().toArray());
        linearFiltersSelectComboBox.setBounds(486, 347, 244, 27);
        getContentPane().add(linearFiltersSelectComboBox);
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

        rosenfeldOperatorLabel = new JLabel("Rosenfeld operator R");
        rosenfeldOperatorLabel.setBounds(375, 548, 190, 30);
        getContentPane().add(rosenfeldOperatorLabel);

        rosenfeldOperatorTextField = new JTextField();
        rosenfeldOperatorTextField.setColumns(10);
        rosenfeldOperatorTextField.setBounds(571, 553, 92, 27);
        getContentPane().add(rosenfeldOperatorTextField);
    }

    private void initializeHistogramOperationsSection(){
        histogramOperationsTextLabel = new JLabel("Histogram operations");
        histogramOperationsTextLabel.setBounds(760, 302, 200, 50);
        getContentPane().add(histogramOperationsTextLabel);

        histogramOperationsCheckBox = new JCheckBox("Variant");
        histogramOperationsCheckBox.setBounds(760, 348, 148, 24);
        getContentPane().add(histogramOperationsCheckBox);

        histogramOperationsSelectComboBox = new JComboBox(HistogramModification.VARIANTS.keySet().toArray());
        histogramOperationsSelectComboBox.setBounds(753, 383, 233, 27);
        getContentPane().add(histogramOperationsSelectComboBox);

        gMinTextField = new JTextField();
        gMinTextField.setColumns(10);
        gMinTextField.setBounds(894, 420, 92, 27);
        getContentPane().add(gMinTextField);

        gMaxTextField = new JTextField();
        gMaxTextField.setColumns(10);
        gMaxTextField.setBounds(894, 455, 92, 27);
        getContentPane().add(gMaxTextField);

        gMinTextLabel = new JLabel("g min");
        gMinTextLabel.setBounds(763, 418, 118, 30);
        getContentPane().add(gMinTextLabel);

        gMaxTextLabel = new JLabel("g max");
        gMaxTextLabel.setBounds(764, 453, 118, 30);
        getContentPane().add(gMaxTextLabel);

    }

    private void initializePathSectionComponents() {
        originalImagePathTextLabel = new JLabel("Original image path");
        originalImagePathTextLabel.setBounds(12, 54, 145, 17);
        getContentPane().add(originalImagePathTextLabel);

        // TODO implement
//        transformedImagePathTextLabel = new JLabel("Transformed image path");
//        transformedImagePathTextLabel.setBounds(12, 153, 206, 17);
//        getContentPane().add(transformedImagePathTextLabel);

        originalImagePathTextInput = new JTextField();
        originalImagePathTextInput.setBounds(12, 83, 491, 27);
        originalImagePathTextInput.setEnabled(false);
        originalImagePathTextInput.setColumns(10);
        getContentPane().add(originalImagePathTextInput);

        // TODO implement
//        transformedImagePathTextInput = new JTextField();
//        transformedImagePathTextInput.setColumns(10);
//        transformedImagePathTextInput.setBounds(12, 171, 491, 27);
//        transformedImagePathTextInput.setEnabled(false);
//        getContentPane().add(transformedImagePathTextInput);

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

    private void initializeFiltrationFrequencyDomainSection(){
        freqDomCheckbox = new JCheckBox("Filtration in frequency domain");
        freqDomCheckbox.setBounds(34, 124, 244, 24);
        getContentPane().add(freqDomCheckbox);

        freqDomFilterCheckBox = new JCheckBox("Filter");
        freqDomFilterCheckBox.setBounds(61, 149, 104, 24);
        getContentPane().add(freqDomFilterCheckBox);

        freqDomFilterSelectComboBox = new JComboBox(FourierFilters.FILTERS.keySet().toArray());
        freqDomFilterSelectComboBox.setBounds(60, 174, 162, 27);
        getContentPane().add(freqDomFilterSelectComboBox);

        freqDomHighPassFilterMaskTextLabel = new JLabel("High-pass filter mask");
        freqDomHighPassFilterMaskTextLabel.setBounds(61, 260, 190, 30);
        getContentPane().add(freqDomHighPassFilterMaskTextLabel);

        freqDomHighPassFilterMaskComboBox = new JComboBox(FourierHighPassMasks.MASKS.keySet().toArray());
        freqDomHighPassFilterMaskComboBox.setBounds(215, 263, 162, 27);
        getContentPane().add(freqDomHighPassFilterMaskComboBox);

        freqDomMinValTextLabel = new JLabel("Min (k)");
        freqDomMinValTextLabel.setBounds(61, 199, 49, 30);
        getContentPane().add(freqDomMinValTextLabel);

        freqDomMinValTextInput = new JTextField();
        freqDomMinValTextInput.setColumns(10);
        freqDomMinValTextInput.setBounds(122, 201, 92, 27);
        getContentPane().add(freqDomMinValTextInput);

        freqDomMaxValTextLabel = new JLabel("Max (l)");
        freqDomMaxValTextLabel.setBounds(61, 234, 49, 30);
        getContentPane().add(freqDomMaxValTextLabel);

        freqDomMaxValTextInput = new JTextField();
        freqDomMaxValTextInput.setColumns(10);
        freqDomMaxValTextInput.setBounds(122, 236, 92, 27);
        getContentPane().add(freqDomMaxValTextInput);

        timeDomHannFilterCheckBox = new JCheckBox("Apply Hann");
        timeDomHannFilterCheckBox.setBounds(169, 148, 104, 24);
        getContentPane().add(timeDomHannFilterCheckBox);
    }

    private void initializeSegmentationSection(){
        segmentationCheckbox = new JCheckBox("Segmentation");
        segmentationCheckbox.setBounds(341, 122, 162, 24);
        getContentPane().add(segmentationCheckbox);

        segmentationComboBox = new JComboBox(Segmentation.SEGMENTATIONS.keySet().toArray());
        segmentationComboBox.setBounds(341, 148, 162, 27);
        getContentPane().add(segmentationComboBox);

        segmentationThresholdTexInput = new JTextField();
        segmentationThresholdTexInput.setColumns(10);
        segmentationThresholdTexInput.setBounds(411, 201, 92, 27);
        getContentPane().add(segmentationThresholdTexInput);

        segmentationThresholdTextLabel = new JLabel("Threshold");
        segmentationThresholdTextLabel.setBounds(310, 199, 104, 30);
        getContentPane().add(segmentationThresholdTextLabel);

        segmentationMinPixForRegionTextInput = new JTextField();
        segmentationMinPixForRegionTextInput.setColumns(10);
        segmentationMinPixForRegionTextInput.setBounds(411, 236, 92, 27);
        getContentPane().add(segmentationMinPixForRegionTextInput);

        segmentationMinPixForRegionTextLabel = new JLabel("Min pix./reg.");
        segmentationMinPixForRegionTextLabel.setBounds(310, 234, 104, 30);
        getContentPane().add(segmentationMinPixForRegionTextLabel);
    }

    private void initializeHelperGrid() {
        Component horizontalStrut = Box.createHorizontalStrut(20);
        horizontalStrut.setBounds(-5, 277, 1003, 10);
        getContentPane().add(horizontalStrut);

        Component verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setBounds(507, 18, 11, 263);
        getContentPane().add(verticalStrut);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        horizontalStrut_1.setBounds(-153, 455, 894, 10);
        getContentPane().add(horizontalStrut_1);

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        verticalStrut_1.setBounds(341, 284, 5, 387);
        getContentPane().add(verticalStrut_1);

        Component verticalStrut_2 = Box.createVerticalStrut(20);
        verticalStrut_2.setBounds(736, 284, 5, 387);
        getContentPane().add(verticalStrut_2);
        
        Component horizontalStrut_2 = Box.createHorizontalStrut(20);
        horizontalStrut_2.setBounds(0, 109, 510, 10);
        getContentPane().add(horizontalStrut_2);
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
