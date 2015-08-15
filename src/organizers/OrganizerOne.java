package organizers;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa przedstawiająca proste panele dzielone.
 *
 * @author sh00x.dev
 */
public class OrganizerOne extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    final JTextArea windowDescription;

    private ImageIcon[] windows = {
            new ImageIcon("src/files/images/lists_1.png"),
            new ImageIcon("src/files/images/tables_2.png"),
            new ImageIcon("src/files/images/trees_3.png"),
            new ImageIcon("src/files/images/comps_4.png"),
            new ImageIcon("src/files/images/progress_5.png"),
            new ImageIcon("src/files/images/orga_6.png")
    };

    private String[] windowsNames = {
            "Listy",
            "Tabele",
            "Drzewa",
            "Komponenty tekstowe",
            "Znaczniki postępu",
            "Organizatory i dekoratory"
    };

    private String[] windowsDescs = {
            "Okno zawierające różnego rodzaju listy.",
            "Okno zawierające różnego rodzaju tabele.",
            "Okno zawierające różnego rodzaju drzewa.",
            "Okno zawierające różnego rodzaju komponenty tekstowe.",
            "Okno zawierające róznego rodzaju wskaźniki postępu.",
            "Okno przedstawiające możliwości organizacyjne wewnątrz okien."
    };

    public OrganizerOne() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //Tworzy komponenty dla prezentacji nazw i opisu planet oraz ich obrazków
        final JList<String> windowList = new JList<>(windowsNames);
        final JLabel windowImage = new JLabel();

        windowDescription = new JTextArea();
        windowDescription.setFont(new Font("System", Font.PLAIN, 14));
        windowDescription.setEditable(false);

        windowList.setSelectedIndex(0);
        windowImage.setIcon(windows[0]);
        windowDescription.setText(windowsDescs[0]);

        windowList.addListSelectionListener(e -> {
            ImageIcon value = windows[windowList.getSelectedIndex()];

            //Aktualizuje obrazek i opis
            windowImage.setIcon(value);
            printDescription(windowList.getSelectedIndex());
        });

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imagePanel.add(windowImage);

        //Tworzy panele dzielone
        JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, windowList, imagePanel);
        innerPane.setContinuousLayout(true);
        innerPane.setOneTouchExpandable(true);

        JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, windowDescription);
        add(outerPane, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Split Pane Frame - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Metoda ustawiająca text JTextArea windowDescription.
     * *
     *
     * @param index index tablicy windows/windowsNames/windowsDescs
     */
    public void printDescription(int index) {
        windowDescription.setText(windowsDescs[index]);
    }
}
