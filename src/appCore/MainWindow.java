package appCore;

import advancedComponents.*;
import clipboard.ClipboardWindow;
import lists.ListWindow;
import printing.PrintingWindow;
import printing.TextPrintingWindow;

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
    private static final int LIST_WINDOW = 0;
    private static final int TABLES_WINDOW = 1;
    private static final int TREES_WINDOW = 2;
    private static final int TEXT_COMP_WINDOW = 3;
    private static final int PROGRESS_POINTERS_WINDOW = 4;
    private static final int ORG_COMPS_AND_DECS = 5;
    private static final int PRINTING_WINDOW = 6;
    private static final int PRINTING_TEXT_WINDOW = 7;
    private static final int PRINTING_IMG_WINDOW = 8;
    private static final int CLIPBOARD_WINDOW = 9;

    private JList<String> componentsList;
    private JLabel imageLabel;

    private ImageIcon[] imageIcons = {
            new ImageIcon("src/files/images/lists_1.png"),
            new ImageIcon("src/files/images/tables_2.png"),
            new ImageIcon("src/files/images/trees_3.png"),
            new ImageIcon("src/files/images/comps_4.png"),
            new ImageIcon("src/files/images/progress_5.png"),
            new ImageIcon("src/files/images/orga_6.png"),
            new ImageIcon("src/files/images/printing_7.png"),
            new ImageIcon("src/files/images/textPrint_8.png"),
            new ImageIcon(),
            new ImageIcon("src/files/images/clipboard_9.png")
    };

    private String[] componentsNames = {
            "1. Listy",
            "2. Tabele",
            "3. Drzewa",
            "4. Komponenty tekstowe",
            "5. Wskaźniki postępu",
            "6. Organizatory komponentów i dekoratory ",
            "[AWT] 7. Drukowanie",
            "[AWT] 8. Drukowanie tekstu",
            "[AWT] 9. Drukowanie obrazu",
            "[AWT] 10. Schowek systemowy"
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
            try {
                int index = componentsList.getSelectedIndex();
                if (index == PRINTING_IMG_WINDOW) {
                    imageLabel.setIcon(null);
                    imageLabel.setText("COMING SOON");
                } else {
                    imageLabel.setText(null);
                    imageLabel.setIcon(imageIcons[index]);
                }
            } catch (Exception ex) {
                System.err.println("Brak obrazu dla danego elementu listy!\n" + ex);
            }
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
        //TODO: All that final elements put to Array and throw away this shit.
        int index = componentsList.getSelectedIndex();
        if (index == LIST_WINDOW) EventQueue.invokeLater(ListWindow::new);
        else if (index == TABLES_WINDOW) EventQueue.invokeLater(TablesWindow::new);
        else if (index == TREES_WINDOW) EventQueue.invokeLater(TreesWindow::new);
        else if (index == TEXT_COMP_WINDOW) EventQueue.invokeLater(TextCompWindow::new);
        else if (index == PROGRESS_POINTERS_WINDOW) EventQueue.invokeLater(ProgressStatusWindow::new);
        else if (index == ORG_COMPS_AND_DECS) EventQueue.invokeLater(OrganizationWindow::new);
        else if (index == PRINTING_WINDOW) EventQueue.invokeLater(PrintingWindow::new);
        else if (index == PRINTING_TEXT_WINDOW) EventQueue.invokeLater(TextPrintingWindow::new);
        else if (index == CLIPBOARD_WINDOW) EventQueue.invokeLater(ClipboardWindow::new);
    }
}
