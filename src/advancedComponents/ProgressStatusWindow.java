package advancedComponents;

import progressBars.ProgressOne;
import progressBars.ProgressThree;
import progressBars.ProgressTwo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ensies on 2015-07-29.
 */
public class ProgressStatusWindow extends JFrame {
    private JButton progressOne;
    private JButton progressTwo;
    private JButton progressThree;

    public ProgressStatusWindow() {
        setLayout(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        progressOne = new JButton("Pasek postępu");
        progressTwo = new JButton("Monitor postępu");
        progressThree = new JButton("Monitorowanie postępu strumieni wejścia");

        progressOne.addActionListener(e -> EventQueue.invokeLater(ProgressOne::new));
        progressTwo.addActionListener(e -> EventQueue.invokeLater(ProgressTwo::new));
        progressThree.addActionListener(e -> EventQueue.invokeLater(ProgressThree::new));

        buttonPanel.add(progressOne);
        buttonPanel.add(progressTwo);
        buttonPanel.add(progressThree);

        add(new JLabel("Wybierz jedno z okien:", JLabel.CENTER));
        add(buttonPanel);

        //Konfiguracja okienka
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno wskaźników postępu - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
