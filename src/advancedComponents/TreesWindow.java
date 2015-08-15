package advancedComponents;

import trees.TreeOne;
import trees.TreeThree;
import trees.TreeTwo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ensies on 2015-07-29.
 */
public class TreesWindow extends JFrame {
    private final int SIZEX = 400;
    private final int SIZEY = 100;

    public TreesWindow() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton treeOne = new JButton("Drzewo 1");
        JButton treeTwo = new JButton("Drzewo 2");
        JButton treeThree = new JButton("Drzewo 3");

        buttonPanel.add(treeOne);
        buttonPanel.add(treeTwo);
        buttonPanel.add(treeThree);

        treeOne.addActionListener(e -> EventQueue.invokeLater(TreeOne::new));
        treeTwo.addActionListener(e -> EventQueue.invokeLater(TreeTwo::new));
        treeThree.addActionListener(e -> EventQueue.invokeLater(TreeThree::new));

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridLayout(2, 1));
        toolsPanel.add(new JLabel("Wybierz jedno z drzew:", JLabel.CENTER));
        toolsPanel.add(buttonPanel);

        add(toolsPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(SIZEX, SIZEY));
        setTitle("Okno drzew - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
