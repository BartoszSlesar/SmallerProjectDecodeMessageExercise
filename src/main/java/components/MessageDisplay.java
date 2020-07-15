package components;


import Utils.CodeMessage;
import Utils.DecodeMessage;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageDisplay extends JTextArea {

    private String fileContent;
    private DecodeMessage decodeMessage;
    private CodeMessage codeMessage;

    public MessageDisplay(String fileContent) {
        this.fileContent = fileContent;
        initializeTextArea();
    }

    public MessageDisplay() {
        initializeTextArea();
    }

    public MessageDisplay(int rows, int columns) {
        super(rows, columns);
        initializeTextArea();
    }

    private void initializeTextArea() {
        decodeMessage = new DecodeMessage();
        codeMessage = new CodeMessage();
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public String getDecodedMessage() {
        return fileContent;
    }

    public void decodeMessage(String fileContent) {
        setText("");
        append(decodeMessage.decodeMessage(fileContent));
        this.fileContent = fileContent;
    }

    public void codeMessage(String fileContent) {
        setText("");
        append(codeMessage.codeMessage(fileContent));
        this.fileContent = fileContent;
    }

}
