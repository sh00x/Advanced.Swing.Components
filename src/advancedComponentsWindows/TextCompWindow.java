package advancedComponentsWindows;

import textComponents.CompOne;
import textComponents.CompThree;
import textComponents.CompTwo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ensies on 2015-07-29.
 */
public class TextCompWindow extends JFrame {
    private final int SIZEX = 400;
    private final int SIZEY = 90;

    private JPanel buttonPanel;

    public TextCompWindow() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton compOne = new JButton("Komponenty tekstowe 1");
        JButton compTwo = new JButton("Komponenty tekstowe 2");
        JButton compThree = new JButton("Komponenty tekstowe 3");

        compOne.addActionListener(e -> EventQueue.invokeLater(CompOne::new));
        compTwo.addActionListener(e -> EventQueue.invokeLater(CompTwo::new));
        compThree.addActionListener(e -> EventQueue.invokeLater(CompThree::new));

        buttonPanel.add(compOne);
        buttonPanel.add(compTwo);
        buttonPanel.add(compThree);

        add(buttonPanel);

        //Konfiguracja fizycznych właściwości okna
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno komponentów tekstowych - @sh00x.dev");
        setPreferredSize(new Dimension(SIZEX, SIZEY));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
