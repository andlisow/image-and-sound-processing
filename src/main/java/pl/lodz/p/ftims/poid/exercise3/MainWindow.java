package main.java.pl.lodz.p.ftims.poid.exercise3;

import main.java.pl.lodz.p.ftims.poid.exercise3.samples.SampleFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public MainWindow(){
        super("Image and Sound Processing - TUL, FTiMS 2015/2016");
        getContentPane().setLayout(null);

        initializeGraphicComponents();
        initializeListeners();
        initWindow();
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
                }
            }
        });

        originalSoundSelectComboBox.addActionListener(e -> {
            String path = ((SampleFiles) originalSoundSelectComboBox.getSelectedItem()).getPath();
            File file = new File(getClass().getClassLoader().getResource(path).getFile());
            originalSoundPathTextInput.setText(file.getPath());
        });
    }

    private void initializeTransformButtonListeners() {
        startTransformButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
