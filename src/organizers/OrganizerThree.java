package organizers;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

/**
 * Ramka pulpitu zawierająca panele wyświetlające zawartość plików HTML
 */
@Deprecated
public class OrganizerThree extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String[] windows = {
            "Listy",
            "Tabele",
            "Drzewa",
            "Komponenty tekstowe",
            "Paski postępu",
            "Organizacja"};
    private JDesktopPane desktop;
    private int nextFrameX;
    private int nextFrameY;
    private int frameDistance;
    private int counter;

    public OrganizerThree() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        desktop = new JDesktopPane();
        add(desktop, BorderLayout.CENTER);

        //Tworzy menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openItem = new JMenuItem("New");

        openItem.addActionListener(e -> {
            createInternalFrame(new JLabel("Test nr.:" + counter), " - kupa test");
            counter = (counter + 1) % windows.length;
        });
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        JMenu windowMenu = new JMenu("Window");
        menuBar.add(windowMenu);
        JMenuItem nextItem = new JMenuItem("Next");
        nextItem.addActionListener(e -> selectNextWindow());
        windowMenu.add(nextItem);

        JMenuItem cascadeItem = new JMenuItem("Cascade");
        cascadeItem.addActionListener(e -> cascadeWindows());
        windowMenu.add(cascadeItem);

        JMenuItem tileItem = new JMenuItem("Title");
        tileItem.addActionListener(e -> tileWindows());
        windowMenu.add(tileItem);

        final JCheckBoxMenuItem dragOutlineItem = new JCheckBoxMenuItem("Drag Outline");
        dragOutlineItem.addActionListener(e -> desktop.setDragMode(dragOutlineItem.isSelected() ? JDesktopPane.OUTLINE_DRAG_MODE : JDesktopPane.LIVE_DRAG_MODE));
        windowMenu.add(dragOutlineItem);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Multiple Document Interface - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
    }

    /**
     * Tworzy wewnętrzną ramkę pulpitu
     *
     * @param component komponent wewnątrz ramki wewnętrznej
     * @param string    tytuł ramki wewnętrznej
     */
    public void createInternalFrame(Component component, String string) {
        //JInternalFrame(Component c, Zmiana rozmiarów, Możliwość zamknięcia, Maksymalizacja, Zwinięcie do ikony
        final JInternalFrame internalFrame = new JInternalFrame(string, true, true, true, true);

        internalFrame.add(component, BorderLayout.CENTER);
        desktop.add(internalFrame);

        internalFrame.addVetoableChangeListener(e -> {
            String name = e.getPropertyName();
            Object value = e.getNewValue();

            //Sprawdza tylko próby zamknięcia ramki
            if (name.equals("closed") && value.equals(true)) {
                //Prosi użytkownika o potwierdzenie zamknięcia
                int result = JOptionPane.showInternalConfirmDialog(internalFrame, "OK to close?", "Select an Option", JOptionPane.YES_NO_OPTION);

                //Jeśli użytkownik się nie zgodzi, to zgłasza weto
                if (result != JOptionPane.YES_OPTION) throw new PropertyVetoException("User canceled close", e);
            }
        });

        //Ustala pozycję ramki
        int width = desktop.getWidth() / 2;
        int height = desktop.getHeight() / 2;
        internalFrame.reshape(nextFrameX, nextFrameY, width, height);
        internalFrame.show();

        //Wybór ramki - może zostać zawetowany
        try {
            internalFrame.setSelected(true);
        } catch (PropertyVetoException ex) {
        }

        frameDistance = internalFrame.getHeight() - internalFrame.getContentPane().getHeight();

        //Oblicza odległość pomiędzy ramkami
        nextFrameX += frameDistance;
        nextFrameY += frameDistance;
        if (nextFrameX + width > desktop.getWidth()) nextFrameX = 0;
        if (nextFrameY + height > desktop.getHeight()) nextFrameY = 0;
    }

    /**
     * Rozmieszcza kaskadowo te ramki pulpitu, które nie są zwinięte do ikony.
     */
    public void cascadeWindows() {
        int x = 0;
        int y = 0;
        int width = desktop.getWidth() / 2;
        int height = desktop.getHeight() / 2;

        for (JInternalFrame frame : desktop.getAllFrames()) {
            if (!frame.isIcon()) {
                try {
                    //Próbuje przeprowadzić ramki do stanu pośredniego, co może zostać zawetowane
                    frame.setMaximum(false);
                    frame.reshape(x, y, width, height);

                    //Zawija wokół brzegu pulpitu
                    if (x + width > desktop.getWidth()) x = 0;
                    if (y + height > desktop.getHeight()) y = 0;
                } catch (PropertyVetoException ex) {
                }
            }
        }
    }

    /**
     * Rozmieszcza sąsiadująco te ramki pulpitu, które nie są zwinięte do ikony
     */
    public void tileWindows() {
        //Zlicza ramki, które nie są zwinięte do ikony
        int frameCount = 0;
        for (JInternalFrame frame : desktop.getAllFrames())
            if (!frame.isIcon()) frameCount++;
        if (frameCount == 0) return;

        int rows = (int) Math.sqrt(frameCount);
        int cols = frameCount / rows;
        int extra = frameCount % rows;
        //Liczba kolumn z dodatkowym wierszem

        int width = desktop.getWidth() / cols;
        int height = desktop.getHeight() % rows;
        int r = 0;
        int c = 0;
        for (JInternalFrame frame : desktop.getAllFrames()) {
            if (!frame.isIcon()) {
                try {
                    frame.setMaximum(false);
                    frame.reshape(c * width, r * height, width, height);
                    r++;
                    if (r == rows) {
                        r = 0;
                        c++;
                        if (c == cols - extra) {
                            //Dodatkowy wiersz
                            rows++;
                            height = desktop.getHeight() / rows;
                        }
                    }
                } catch (PropertyVetoException ex) {
                }
            }
        }

    }

    /**
     * Wybiera ramkę
     */
    public void selectNextWindow() {
        JInternalFrame[] frames = desktop.getAllFrames();
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isSelected()) {
                //Znajduje ramkę, która nie jest jeszcze zwinięta do ikony i może zostać wybrana
                int next = (i + 1) % frames.length;
                while (next != i) {
                    if (frames[next].isIcon()) {
                        try {
                            //Pozostałe ramki są zwinięte do ikon lub zgłosił weto do wyboru
                            frames[next].setSelected(true);
                            frames[next].toFront();
                            frames[i].toBack();
                            return;
                        } catch (PropertyVetoException ex) {
                        }
                        next = (next + 1) % frames.length;
                    }
                }
            }
        }
    }
}
