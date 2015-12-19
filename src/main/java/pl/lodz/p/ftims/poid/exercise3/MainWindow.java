package main.java.pl.lodz.p.ftims.poid.exercise3;

import main.java.pl.lodz.p.ftims.poid.exercise1_2.operations.filters.linear.LinearFilter;
import main.java.pl.lodz.p.ftims.poid.exercise1_2.samples.filters.linear.LinearFilters;
import main.java.pl.lodz.p.ftims.poid.exercise3.model.WavFile;
import main.java.pl.lodz.p.ftims.poid.exercise3.operations.Operations;
import main.java.pl.lodz.p.ftims.poid.exercise3.samples.FreqDomTransformations;
import main.java.pl.lodz.p.ftims.poid.exercise3.samples.SampleFiles;
import main.java.pl.lodz.p.ftims.poid.exercise3.samples.TimeDomTransformations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author alisowsk
 */
public class MainWindow extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(MainWindow.class);

    // paths section
    private JLabel originalSoundPathTextLabel;
    private JTextField originalSoundPathTextInput;
    private JButton originalSoundFileChooserButton;
    private JComboBox originalSoundSelectComboBox;
    private JFileChooser fileChooser;

    // menu section
    private JMenuBar mainMenuBar;
    private JMenu helpMenuSection;
    private JMenu fileMenuSection;

    // transform button section
    private JButton startTransformButton;

    // methods
    private JCheckBox timeDomMethodsCheckbox;
    private JComboBox timeDomMethodsComboBox;
    private JCheckBox freqDomMethodsCheckbox;
    private JComboBox freqDomMethodsComboBox;

    private Operations operations;
    private WavFile sourceFile;

    public MainWindow(){
        super("Image and Sound Processing - TUL, FTiMS 2015/2016");
        getContentPane().setLayout(null);

        initializeLogicComponents();
        initializeGraphicComponents();
        initializeListeners();
        initWindow();
    }

    private void initializeLogicComponents() {
        operations = new Operations();
    }

    private void initializeGraphicComponents() {
        initializePathSectionComponents();
        initializeMenuComponents();
        initializeTransformButtonSection();
    }

    private void initializeListeners(){
        initializePathSectionListeners();
        initializeTransformButtonListeners();
    }

    private void initializePathSectionListeners() {
        originalSoundFileChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int returnVal = fileChooser.showOpenDialog(MainWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    originalSoundPathTextInput.setText(file.getPath());
                    try {
                        sourceFile = WavFile.openWavFile(file);
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                        AudioFormat format = audioInputStream.getFormat();
                        long frames = audioInputStream.getFrameLength();
                        double durationInSeconds = (frames+0.0) / format.getFrameRate();
                        sourceFile.setDuration(durationInSeconds);
                        sourceFile.setName(file.getName());
                    } catch (Exception ex) {
                        LOG.error("An error occurred when trying to read .wav file", e);
                    }
                }
            }
        });

        originalSoundSelectComboBox.addActionListener(e -> {
            String path = ((SampleFiles) originalSoundSelectComboBox.getSelectedItem()).getPath();
            File file = new File(getClass().getClassLoader().getResource(path).getFile());
            originalSoundPathTextInput.setText(file.getPath());
            try {
                sourceFile = WavFile.openWavFile(file);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                long frames = audioInputStream.getFrameLength();
                double durationInSeconds = (frames+0.0) / format.getFrameRate();
                sourceFile.setDuration(durationInSeconds);
                sourceFile.setName(file.getName());
            } catch (Exception ex) {
                LOG.error("An error occurred when trying to read .wav file", e);
            }
        });
    }

    private void initializeTransformButtonListeners() {
        startTransformButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                operations.clear();
                if(timeDomMethodsCheckbox.isSelected()){
                    for (String timeDomMethod : TimeDomTransformations.TRANSFORMATIONS.keySet()) {
                        if (timeDomMethod.equals(timeDomMethodsComboBox.getSelectedItem())) {
                            operations.addOperation(TimeDomTransformations.TRANSFORMATIONS.get(timeDomMethod));
                        }
                    }
                }
                if(freqDomMethodsCheckbox.isSelected()){
                    for (String freqDomMethod : FreqDomTransformations.TRANSFORMATIONS.keySet()) {
                        if (freqDomMethod.equals(freqDomMethodsComboBox.getSelectedItem())) {
                            operations.addOperation(FreqDomTransformations.TRANSFORMATIONS.get(freqDomMethod));
                        }
                    }
                }
                operations.processSound(sourceFile);
            }
        });
    }

    private void initializePathSectionComponents() {
        originalSoundPathTextLabel = new JLabel("Original sound path");
        originalSoundPathTextLabel.setBounds(12, 54, 145, 17);
        getContentPane().add(originalSoundPathTextLabel);

        originalSoundPathTextInput = new JTextField();
        originalSoundPathTextInput.setBounds(12, 83, 491, 27);
        originalSoundPathTextInput.setEnabled(false);
        originalSoundPathTextInput.setColumns(10);
        getContentPane().add(originalSoundPathTextInput);

        originalSoundFileChooserButton = new JButton("Choose sound");
        originalSoundFileChooserButton.setBounds(358, 42, 145, 29);
        getContentPane().add(originalSoundFileChooserButton);

        fileChooser = new JFileChooser();

        originalSoundSelectComboBox = new JComboBox(SampleFiles.values());
        originalSoundSelectComboBox.setToolTipText("Select input sound");
        originalSoundSelectComboBox.setBounds(148, 49, 198, 27);
        getContentPane().add(originalSoundSelectComboBox);
    }

    private void initializeTransformButtonSection() {
        startTransformButton = new JButton("Transform!");
        startTransformButton.setBounds(808, 563, 155, 82);
        getContentPane().add(startTransformButton);
        
        timeDomMethodsCheckbox = new JCheckBox("Time domain methods");
        timeDomMethodsCheckbox.setBounds(12, 157, 224, 24);
        getContentPane().add(timeDomMethodsCheckbox);
        
        timeDomMethodsComboBox = new JComboBox(TimeDomTransformations.TRANSFORMATIONS.keySet().toArray());
        timeDomMethodsComboBox.setBounds(12, 195, 162, 27);
        getContentPane().add(timeDomMethodsComboBox);
        
        freqDomMethodsCheckbox = new JCheckBox("Frequency domain methods");
        freqDomMethodsCheckbox.setBounds(279, 157, 271, 24);
        getContentPane().add(freqDomMethodsCheckbox);
        
        freqDomMethodsComboBox = new JComboBox(FreqDomTransformations.TRANSFORMATIONS.keySet().toArray());
        freqDomMethodsComboBox.setBounds(279, 196, 162, 27);
        getContentPane().add(freqDomMethodsComboBox);
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
        this.setSize(1000, 700);
    }
}
