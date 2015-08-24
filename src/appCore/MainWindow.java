package appCore;

import advancedComponents.*;
import clipboard.ClipboardWindow;
import clipboard.DragAndDrop;
import clipboard.ImageClipboardWindow;
import clipboard.JavaClipboardWindow;
import lists.ListWindow;
import osIntegration.DesktopApps;
import osIntegration.SystemTrayTest;
import other.OtherThingsWindow;
import printing.PrintingWindow;
import printing.TextPrintingWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    private static final int IMAGE_CLIPBOARD_WINDOW = 10;
    private static final int JAVA_CLIPBOARD_WINDOW = 11;
    private static final int DRAG_N_DROP_WINDOW = 12;
    private static final int DESKTOP_APPS_WINDOW = 13;
    private static final int OTHER_RESOURCES = 14;

    private JList<String> componentsList;
    private JLabel imageLabel;
    private JMenuBar menuBar;
    private boolean trayIcon = false;
    private JButton openWindowButton;

    //TODO: Make static public and use in evy other class
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
            new ImageIcon("src/files/images/clipboard_9.png"),
            new ImageIcon("src/files/images/imageClip_10.png"),
            new ImageIcon("src/files/images/colors_11.png"),
            new ImageIcon("src/files/images/drag_12.png"),
            new ImageIcon("src/files/images/desktopApp_13.png"),
            new ImageIcon("src/files/images/threads_1.png")
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
            "[AWT] 10. Schowek systemowy",
            "[AWT] 11. Schowek systemowy - obrazy",
            "[AWT] 12. Schowek systemowy - Java",
            "[AWT] 13. Przeciągnij i upuść",
            "[AWT] 14. Aplikacje systemowe",
            "Other"
    };

    public MainWindow() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JMenu helpMenu = new JMenu("Pomoc");
        JMenuItem aboutItem = new JMenuItem("O programie");
        JMenuItem helpItem = new JMenuItem("Pomoc");
        JMenuItem trayItem = new JMenuItem("Pokaż w zasobniku");

        menuBar = new JMenuBar();
        menuBar.setBorderPainted(true);
        menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);   //menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);

        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Coming soon", "Coming soon", JOptionPane.INFORMATION_MESSAGE));

        aboutItem.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this, readWelcomeDialog(), "O programie", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        trayItem.addActionListener(e -> {
            if (!trayIcon) {
                SystemTrayTest.init();
                trayIcon = true;
            } else {
                JOptionPane.showMessageDialog(this, "Możesz dodać tylko jedną ikonę do zasobnika systemowego!", "Ostrzeżenie", JOptionPane.ERROR_MESSAGE);
            }
        });

        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();
        helpMenu.add(trayItem);
        setJMenuBar(menuBar);

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
        openWindowButton = new JButton("Otwórz przykład w nowym oknie");

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
                if (e.getClickCount() == 2) {
                    openSelectedWindow();
                }
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

        componentsList.addListSelectionListener(e -> {
            try {
                int index = componentsList.getSelectedIndex();
                if (imageIcons[index].getImage() == null) {
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
        else if (index == IMAGE_CLIPBOARD_WINDOW) EventQueue.invokeLater(ImageClipboardWindow::new);
        else if (index == JAVA_CLIPBOARD_WINDOW) EventQueue.invokeLater(JavaClipboardWindow::new);
        else if (index == DRAG_N_DROP_WINDOW) EventQueue.invokeLater(DragAndDrop::new);
        else if (index == DESKTOP_APPS_WINDOW) EventQueue.invokeLater(DesktopApps::new);
        else if (index == OTHER_RESOURCES) EventQueue.invokeLater(OtherThingsWindow::new);
    }

    /**
     * Wczytuje dane do okna powitalnego
     */
    private String readWelcomeDialog() throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader("src/appCore/cfg/infoDialog.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}
