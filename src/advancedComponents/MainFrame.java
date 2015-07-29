package advancedComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sh00x.dev
 * Simple application with Advanced Swing Components
 * @author sh00x.dev
 */
public class MainFrame extends JFrame {
    private final static int LIST_WINDOW = 0;
    private final static int TABLES_WINDOW = 1;
    private final static int TREES_WINDOW = 2;
    private final static int TEXT_COMP_WINDOW = 3;
    private final static int PROGRESS_POINTERS_WINDOW = 4;
    private final static int ORG_COMPS_AND_DECS = 5;

    JPanel mainPanel;
    JPanel imagePanel;
    JList<String> componentsList;
    JScrollPane listScrollPane;
    JButton openWindowButton;

    String[] componentsNames = { "1. Listy", "2. Tabele", "3. Drzewa", "4. Komponenty tekstowe",
            "5. Wskaźniki postępu", "6. Organizatory komponentów i dekoratory " };

    public MainFrame() {
        String imageReference = "src/files/ListMouseSelection.png";

        //Konfiguracja listy oraz dodanie jej do JScrollPane
        componentsList = new JList<>(componentsNames);
        componentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listScrollPane = new JScrollPane(componentsList);
        openWindowButton = new JButton("Otwórz przykład w nowym oknie");

        openWindowButton.addActionListener(e -> {
            int index = componentsList.getSelectedIndex();
            switch (index) {
                case LIST_WINDOW: {
                    EventQueue.invokeLater(ListWindow::new);
                    break;
                }
                case TABLES_WINDOW: {
                    EventQueue.invokeLater(TablesWindow::new);
                    break;
                }
                case TREES_WINDOW: {
                    EventQueue.invokeLater(TreesWindow::new);
                    break;
                }
                case TEXT_COMP_WINDOW: {
                    EventQueue.invokeLater(TextCompWindow::new);
                    break;
                }
                case PROGRESS_POINTERS_WINDOW: {
                    EventQueue.invokeLater(ProgressPointersWindow::new);
                    break;
                }
                case ORG_COMPS_AND_DECS: {
                    EventQueue.invokeLater(OrgAndDecsWindow::new);
                    break;
                }
            }
        });

        //Konfiguracja imagePanel oraz dodanie do niego elementów
        JPanel openButtonPanel = new JPanel();
        openButtonPanel.setLayout(new FlowLayout());
        openButtonPanel.add(openWindowButton);

        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(new JLabel(new ImageIcon(imageReference)), BorderLayout.CENTER);
        imagePanel.add(openButtonPanel, BorderLayout.SOUTH);

        //Konfiguracja mainPanel oraz dodanie do niego elementów
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(imagePanel);
        add(mainPanel);

        //Konfiguracja fizycznych właściwości ramki
        final int SIZE_X = 800;
        final int SIZE_Y = 400;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Advanced Swing Components - @sh00x.dev");
        setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
