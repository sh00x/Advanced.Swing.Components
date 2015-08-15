package progressBars;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Klasa wczytująca dany plik tekstowy do pola tekstowego,
 * pokazując przy tym pasek postępu.
 */
public class ProgressThree extends JFrame {
    private JMenuItem openItem;
    private JMenuItem exitItem;
    private JTextPane textPane;
    private JFileChooser chooser;
    private StyledDocument doc;
    private SimpleAttributeSet centerAligment;

    public ProgressThree() {
        textPane = new JTextPane();
        textPane.setText("Tutaj pojawi się tekst z pliku który wczytasz.\nPamiętaj, aby wczytywać tylko pliki .txt!");
        textPane.setEditable(false);
        textPane.setFont(new Font("System", Font.PLAIN, 14));

        //Zapewnia formatowanie tekstu
        doc = textPane.getStyledDocument();
        centerAligment = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAligment, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), centerAligment, false);

        JScrollPane textScrollPane = new JScrollPane(textPane);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(textScrollPane);

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("src/files"));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> {
            try {
                openFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        fileMenu.add(openItem);
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> {
            dispose();
        });

        fileMenu.add(exitItem);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno wskaźników postępu 3 - @sh00x.dev");
        setPreferredSize(new Dimension(400, 400));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public void openFile() throws IOException {
        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION) return;
        final File f = chooser.getSelectedFile();

        //Tworzy strumień i sekwencję filtrów odczytu
        InputStream fileIn = Files.newInputStream(f.toPath());
        final ProgressMonitorInputStream progressIn = new ProgressMonitorInputStream(this, "Reading " + f.getName(), fileIn);

        textPane.setText("");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (Scanner scanner = new Scanner(progressIn)) {
                    while (scanner.hasNextLine()) {
                        appendString(scanner.nextLine(), getDoc(), getCenterAligment());
                        appendString("\n", getDoc(), getCenterAligment());
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    public void appendString(String str, StyledDocument document, SimpleAttributeSet set) throws BadLocationException {
        document.insertString(document.getLength(), str, set);
    }

    public StyledDocument getDoc() {
        return doc;
    }

    public SimpleAttributeSet getCenterAligment() {
        return centerAligment;
    }
}
