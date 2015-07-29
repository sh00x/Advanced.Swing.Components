package advancedComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ensies on 2015-07-29.
 */
public class TablesWindow extends JFrame {
    private final int SIZEX = 400;
    private final int SIZEY = 400;

    public TablesWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno tabel - @sh00x.dev");
        setPreferredSize(new Dimension(SIZEX, SIZEY));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
