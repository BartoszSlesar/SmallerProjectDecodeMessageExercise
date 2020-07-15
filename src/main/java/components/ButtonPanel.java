package components;

import Interfaces.FileProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ButtonPanel extends JPanel implements ActionListener {

    private Dimension dimension;
    private int width;
    private int height;
    private JButton openFile;
    private JButton decodeFile;
    private JButton codeFile;
    private JFileChooser fileChooser;
    private FileProcessor fileProcessor;

    public ButtonPanel() {
        setDimension(new Dimension(200, 200));
        fileChooser = new JFileChooser("N:\\JavaFilesProjects");
        initializePanel();

    }

    public ButtonPanel(FileProcessor fileProcessor) {
        setDimension(new Dimension(200, 200));
        fileChooser = new JFileChooser("N:\\JavaFilesProjects");
        this.fileProcessor = fileProcessor;
        initializePanel();

    }

    public ButtonPanel(Dimension dimension, FileProcessor fileProcessor) {
        this.dimension = dimension;
        this.width = dimension.width;
        this.height = dimension.height;
        fileChooser = new JFileChooser("N:\\JavaFilesProjects");

        this.fileProcessor = fileProcessor;
        initializePanel();

    }

    public ButtonPanel(Dimension dimension) {
        this.dimension = dimension;
        this.width = dimension.width;
        this.height = dimension.height;
        fileChooser = new JFileChooser("N:\\JavaFilesProjects");
        initializePanel();
    }


    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.width = dimension.width;
        this.height = dimension.height;
        this.dimension = dimension;
    }

    private void initializePanel() {
        setPreferredSize(dimension);
        setLayout(new BorderLayout(5, 5));
        createButtons();
        add(openFile, BorderLayout.LINE_START);
        add(decodeFile, BorderLayout.CENTER);
        add(codeFile, BorderLayout.LINE_END);
    }

    private void createButtons() {
        Dimension dimP = new Dimension(200, 100);
        Dimension dimM = new Dimension(200, 100);

        openFile = new JButton("Open File");
        openFile.setPreferredSize(dimP);
        openFile.addActionListener(this);

        decodeFile = new JButton("Decode File");
        decodeFile.setPreferredSize(dimP);
        decodeFile.addActionListener(this);

        codeFile = new JButton("Code File");
        codeFile.setPreferredSize(dimP);
        codeFile.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Open File")) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getAbsolutePath().contains(".txt")) {
                    String message = "Error! please select onl txt files";
                    JOptionPane.showMessageDialog(this,
                            message, "Incorrect File Format", JOptionPane.ERROR_MESSAGE);
                } else {
                    fileProcessor.getFileFromChooser(file);
                }


            }
        } else {
            fileProcessor.fileActionTriggered(e.getActionCommand());
        }
    }


    public void setFileProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }
}
