package organizers;

import javax.swing.*;
import java.awt.*;

/**
 * Ramka zawierająca panel z zakładkami oraz przyciski
 * umożliwiające przełączanie sposobu prezentacji zakładek
 */
public class OrganizerTwo extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    private JTabbedPane tabbedPane;

    public OrganizerTwo() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        tabbedPane = new JTabbedPane();

        //Załadowanie komponentów zakładek odkładamy
        //do moementu ich pierwszej prezentacji
        tabbedPane.addTab("Listy", null);
        tabbedPane.addTab("Tabele", null);
        tabbedPane.addTab("Drzewa", null);
        tabbedPane.addTab("Kompozyty tekstowe", null);
        tabbedPane.addTab("Paski postępu", null);
        tabbedPane.addTab("Organizatory i kompozycje", null);
        loadTab(0); //Nie licząc tego

        tabbedPane.addChangeListener(e -> {
            //Sprawdza czy na zakłdace umieszczony jest już komponent
            if (tabbedPane.getSelectedComponent() == null) {
                //Wczytuje obrazek ikony
                int index = tabbedPane.getSelectedIndex();
                loadTab(index);
            }
        });

        JPanel buttonPanel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();

        JRadioButton wrapButton = new JRadioButton("Wrap tabs");
        JRadioButton scrollButton = new JRadioButton("Scroll tabs");
        wrapButton.addActionListener(e -> tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT));
        scrollButton.addActionListener(e -> tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT));

        buttonGroup.add(scrollButton);
        buttonGroup.add(wrapButton);
        buttonPanel.add(scrollButton);
        buttonPanel.add(wrapButton);

        add(tabbedPane, BorderLayout.CENTER);    //"Center"
        add(buttonPanel, BorderLayout.SOUTH);    //"South"

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tabbed Pane Frame - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
    }

    /**
     * Wczytuje zakładkę o podanym indeksie.
     *
     * @param index indeks ładowanej zakładki
     */
    private void loadTab(int index) {
        ImageIcon[] windows = {
                new ImageIcon("src/files/images/lists_1.png"),
                new ImageIcon("src/files/images/tables_2.png"),
                new ImageIcon("src/files/images/trees_3.png"),
                new ImageIcon("src/files/images/comps_4.png"),
                new ImageIcon("src/files/images/progress_5.png"),
                new ImageIcon("src/files/images/orga_6.png")
        };

        tabbedPane.setComponentAt(index, new JLabel(windows[index]));
    }
}
