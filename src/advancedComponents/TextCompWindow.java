package advancedComponents;

import textComponents.CompFour;
import textComponents.CompOne;
import textComponents.CompThree;
import textComponents.CompTwo;

import javax.swing.*;
import java.awt.*;

/**
 * Okno zawierające przyciski otwierające
 * kolejne JFrame z różnymi komponentami
 * tekstowymi
 */
public class TextCompWindow extends JFrame {
    private JPanel buttonPanel;

    public TextCompWindow() {
        setLayout(new GridLayout(2, 1));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton compOne = new JButton("Komponenty tekstowe 1");
        JButton compTwo = new JButton("Komponenty tekstowe 2");
        JButton compThree = new JButton("Komponenty tekstowe 3");
        JButton compFour = new JButton("Komponenty tekstowe 4");

        compOne.addActionListener(e -> EventQueue.invokeLater(CompOne::new));
        compTwo.addActionListener(e -> EventQueue.invokeLater(CompTwo::new));
        compThree.addActionListener(e -> EventQueue.invokeLater(CompThree::new));
        compFour.addActionListener(e -> EventQueue.invokeLater(CompFour::new));

        buttonPanel.add(compOne);
        buttonPanel.add(compTwo);
        buttonPanel.add(compThree);
        buttonPanel.add(compFour);

        add(new JLabel("Wybierz jeden z komponentów:", JLabel.CENTER));
        add(buttonPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno komponentów tekstowych - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
