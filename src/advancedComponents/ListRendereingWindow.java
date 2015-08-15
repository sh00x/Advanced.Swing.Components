package advancedComponents;

import additionalResources.FontCellRenderer;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Okno zawierające listę czcionek, które są renderowane na bieżąco.
 *
 * @author sh00x.dev
 */
public class ListRendereingWindow extends JFrame {
    public ListRendereingWindow() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        JTextPane textPane = new JTextPane();
        //Zapewnia formatowanie tekstu
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        try {
            setPaneText("srcfiles/textFiles/listek.txt", textPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textPane.setBorder(BorderFactory.createEtchedBorder());

        Font[] fonts = getFonts();

        JList<Font> fontList = new JList<>(fonts);
        fontList.setCellRenderer(new FontCellRenderer());
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontList.addListSelectionListener(e -> textPane.setFont(fontList.getSelectedValue()));

        JScrollPane fontScrollPane = new JScrollPane(fontList);
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        listPanel.add(fontScrollPane);

        JScrollPane textScrollPane = new JScrollPane(textPane);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(textScrollPane, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno list - @sh00x.dev");
        int SIZEX = 400;
        int SIZEY = 400;
        setPreferredSize(new Dimension(SIZEX, SIZEY));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Ustawia zawartość przekazanej JTextArea na tekst zawarty w pliku tekstowym
     *
     * @param path     Path do pliku tekstowego
     * @param textPane JTextArea przekazana jako referencja
     * @throws IOException
     */
    private void setPaneText(String path, JTextPane textPane) throws IOException {
        File textFile = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(textFile));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line).append("\n");

        textPane.setText(builder.toString());
        reader.close();
    }

    /**
     * Funkcja sprawdzająca jakie czcionki dostępne są w systemie
     *
     * @return Font[] dostępnych w systemie
     */
    private Font[] getFonts() {
        String[] fontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Font[] fonts = new Font[fontFamilyNames.length];

        for (int i = 0; i < fonts.length; i++)
            fonts[i] = new Font(fontFamilyNames[i], Font.PLAIN, 14);

        return fonts;
    }
}
