package advancedComponentsWindows;

import additionalResources.FontCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class ListRendereingWindow extends JFrame {
    public ListRendereingWindow() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        JTextArea textArea = new JTextArea(10, 15);
        textArea.setFont(new Font("System", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        try {
            setAreaText("src/files/Ostatnie życzenie.txt", textArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setBorder(BorderFactory.createEtchedBorder());

        Font[] fonts = {new Font("Serif", Font.PLAIN, 14), new Font("SansSerif", Font.PLAIN, 14),
                new Font("Monospaced", Font.PLAIN, 14), new Font("Dialog", Font.PLAIN, 14),
                new Font("Times New Roman", Font.PLAIN, 14), new Font("Arial", Font.PLAIN, 14)};

        JList<Font> fontList = new JList<>(fonts);
        fontList.setCellRenderer(new FontCellRenderer());
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontList.addListSelectionListener(e -> textArea.setFont(fontList.getSelectedValue()));

        JScrollPane fontScrollPane = new JScrollPane(fontList);
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        listPanel.add(fontScrollPane);

        JScrollPane textScrollPane = new JScrollPane(textArea);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(textScrollPane, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.SOUTH);
        add(mainPanel);

        //Ustawienia fizycznych właściwości okna
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
     * @param textArea JTextArea przekazana jako referencja
     * @throws IOException
     */
    private void setAreaText(String path, JTextArea textArea) throws IOException {
        File textFile = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(textFile));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line + "\n");

        textArea.setText(builder.toString());
        reader.close();
    }
}
