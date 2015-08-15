package appCore;

import advancedComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by sh00x.dev
 * Simple application with Advanced Swing Components
 *
 * @author sh00x.dev
 */
public class MainWindow extends JFrame {
    private final static int LIST_WINDOW = 0;
    private final static int TABLES_WINDOW = 1;
    private final static int TREES_WINDOW = 2;
    private final static int TEXT_COMP_WINDOW = 3;
    private final static int PROGRESS_POINTERS_WINDOW = 4;
    private final static int ORG_COMPS_AND_DECS = 5;

    private JList<String> componentsList;
    private JLabel imageLabel;

    private ImageIcon[] imageIcons = {
            new ImageIcon("src/files/images/lists_1.png"),
            new ImageIcon("src/files/images/tables_2.png"),
            new ImageIcon("src/files/images/trees_3.png"),
            new ImageIcon("src/files/images/comps_4.png"),
            new ImageIcon("src/files/images/progress_5.png"),
            new ImageIcon("src/files/images/orga_6.png")};

    private String[] componentsNames = {
            "1. Listy",
            "2. Tabele",
            "3. Drzewa",
            "4. Komponenty tekstowe",
            "5. Wskaźniki postępu",
            "6. Organizatory komponentów i dekoratory "
    };

    public MainWindow() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        //JLabel zawierająca ilustrację elementu
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setIcon(imageIcons[0]);

        //Konfiguracja listy oraz dodanie jej do JScrollPane
        componentsList = new JList<>(componentsNames);
        componentsList.add(new JSeparator());
        componentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        componentsList.setSelectedIndex(LIST_WINDOW);

        JScrollPane listScrollPane = new JScrollPane(componentsList);
        JButton openWindowButton = new JButton("Otwórz przykład w nowym oknie");

        componentsList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   openSelectedWindow();
                }
            }
        });

        componentsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    openSelectedWindow();
                }
            }
        });

        componentsList.addListSelectionListener(e -> {
            int index = componentsList.getSelectedIndex();
            imageLabel.setIcon(imageIcons[index]);
        });

        openWindowButton.addActionListener(e -> openSelectedWindow());

        //Konfiguracja imagePanel oraz dodanie do niego elementów
        JPanel openButtonPanel = new JPanel();
        openButtonPanel.setLayout(new FlowLayout());
        openButtonPanel.add(openWindowButton);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(openButtonPanel, BorderLayout.SOUTH);

        //Konfiguracja mainPanel oraz dodanie do niego elementów
        JPanel mainPanel = new JPanel();
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

    /**
     * Otwiera wybrane przez użytkownika okno.
     */
    public void openSelectedWindow() {
        int index = componentsList.getSelectedIndex();
        if (index == LIST_WINDOW) EventQueue.invokeLater(ListWindow::new);
        else if (index == TABLES_WINDOW) EventQueue.invokeLater(TablesWindow::new);
        else if (index == TREES_WINDOW) EventQueue.invokeLater(TreesWindow::new);
        else if (index == TEXT_COMP_WINDOW) EventQueue.invokeLater(TextCompWindow::new);
        else if (index == PROGRESS_POINTERS_WINDOW) EventQueue.invokeLater(ProgressStatusWindow::new);
        else if (index == ORG_COMPS_AND_DECS) EventQueue.invokeLater(OrganizationWindow::new);
    }
}
