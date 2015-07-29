package advancedComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ensies on 2015-07-29.
 */
public class TextCompWindow extends JFrame {
    private final int SIZEX = 400;
    private final int SIZEY = 400;

    public TextCompWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno komponentów tekstowych - @sh00x.dev");
        setPreferredSize(new Dimension(SIZEX, SIZEY));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
