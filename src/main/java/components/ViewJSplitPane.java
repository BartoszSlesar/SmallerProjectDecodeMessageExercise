package components;

import Interfaces.FileProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class ViewJSplitPane extends JPanel implements FileProcessor {
    private Dimension dimension;
    private int height;
    private int width;
    private JScrollPane uploadedFileScroll;
    private JScrollPane decodedMessageScroll;
    private JTextArea oppenedFile;
    private MessageDisplay messageDisplay;
    private StringBuilder fileContent;
    private Pattern pattern = Pattern.compile("[^a-zA-Z\\s]?\\d[^a-zA-Z\\s]?");

    public ViewJSplitPane() {
        super(new BorderLayout());
        this.dimension = new Dimension(200, 200);
        this.height = dimension.height;
        this.width = dimension.width;
        fileContent = new StringBuilder();
        createScrollPanes();
        initllizeJPanel();
    }


    public ViewJSplitPane(Dimension dimension) {
        super(new BorderLayout());
        this.dimension = dimension;
        this.height = dimension.height;
        this.width = dimension.width;
        messageDisplay = new MessageDisplay();
        fileContent = new StringBuilder();
        createScrollPanes();
        initllizeJPanel();
    }

    private void initllizeJPanel() {
        setPreferredSize(dimension);
        add(createJSplitPane(), BorderLayout.CENTER);
    }

    private JSplitPane createJSplitPane() {
        JSplitPane contentSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                oppenedFile, messageDisplay);

        //only in some cases
        contentSplit.setContinuousLayout(true);
        contentSplit.setOneTouchExpandable(true);
        contentSplit.setResizeWeight(0.5);
        return contentSplit;
    }

    //TODO fix Scroll Pane Bug
    private void createScrollPanes() {
        uploadedFileScroll = createScrollPane();
        uploadedFileScroll.add(new JTextArea());

        decodedMessageScroll = createScrollPane();
        decodedMessageScroll.add(decodedMessage());
    }

    private JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(height / 2, width / 2));
        scrollPane.setMinimumSize(new Dimension(100, 100));
        return scrollPane;
    }

    private JTextArea decodedMessage() {
        oppenedFile = new JTextArea();
        oppenedFile.setLineWrap(true);
        oppenedFile.setWrapStyleWord(true);

        return oppenedFile;
    }


    @Override
    public void getFileFromChooser(File file) {
        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {

            stream.forEach(val -> fileContent.append(val));

        } catch (IOException e) {
            e.printStackTrace();
        }
        oppenedFile.setText(fileContent.toString());
    }

    @Override
    public void fileActionTriggered(String source) {
        if (source.equals("Decode File")) {
            System.out.println(fileContent.toString());
            if (oppenedFile.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "There is no text to Decode",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                fileContent.delete(0, fileContent.length());
                fileContent.append(oppenedFile.getText());
                messageDisplay.decodeMessage(fileContent.toString());
            }

        } else {
            fileContent.delete(0, fileContent.length());
            fileContent.append(oppenedFile.getText());
            System.out.println(fileContent.toString() + " tutaj sads");
            if (pattern.matcher(fileContent.toString()).find() || fileContent.toString().equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error You can obly code non numeric values or There is no text",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } else {
                messageDisplay.codeMessage(fileContent.toString());
            }

        }

    }

    public String getFileToSave() {
        return messageDisplay.getText().equals("") ? "" : messageDisplay.getText();
    }
}
