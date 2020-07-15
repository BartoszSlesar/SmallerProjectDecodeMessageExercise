package main;

import components.ButtonPanel;
import components.ViewJSplitPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DecodeMessageMain extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;
    private Dimension fDimension = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private ViewJSplitPane viewJSplitPane;

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        // JMenuItem item = new JMenuItem("Save",new ImageIcon("N:\\JavaFilesProjects\\save.jpg"));
        JMenuItem item = new JMenuItem("Save");
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        item.addActionListener(this);

        menu.add(item);

        menuBar.add(menu);
        return menuBar;
    }

    public JPanel mainContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setPreferredSize(fDimension);
        viewJSplitPane = new ViewJSplitPane(fDimension);
        panel.add(viewJSplitPane);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        ButtonPanel buttonPanel = new ButtonPanel(new Dimension(FRAME_WIDTH, 100));
        buttonPanel.setFileProcessor(viewJSplitPane);
        panel.add(buttonPanel);

        return panel;
    }


    public void setUpJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenu());
        setTitle("Decode a message");
        add(mainContent());
        pack();
        setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DecodeMessageMain decodeMessageMain = new DecodeMessageMain();
            decodeMessageMain.setUpJFrame();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            filePath = filePath.contains(".txt") ? filePath : filePath + ".txt";
            String valueToSave = viewJSplitPane.getFileToSave();
            try {
                BufferedWriter out = Files.newBufferedWriter(Paths.get(filePath));
                out.write(valueToSave);
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
