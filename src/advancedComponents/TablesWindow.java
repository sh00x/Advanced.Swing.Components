package advancedComponents;

import tables.TableOne;
import tables.TableThree;
import tables.TableTwo;

import javax.swing.*;
import java.awt.*;

/**
 * Okno zawierające przyciski otwierające
 * poszczególne tabele
 */
public class TablesWindow extends JFrame {

    public TablesWindow() {
        JButton tableOne = new JButton("Tabela 1");
        JButton tableTwo = new JButton("Tabela 2");
        JButton tableThree = new JButton("Tabela 3");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(tableOne);
        buttonPanel.add(tableTwo);
        buttonPanel.add(tableThree);

        tableOne.addActionListener(e -> EventQueue.invokeLater(TableOne::new));
        tableTwo.addActionListener(e -> EventQueue.invokeLater(TableTwo::new));
        tableThree.addActionListener(e -> EventQueue.invokeLater(TableThree::new));

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridLayout(2, 1));
        toolsPanel.add(new JLabel("Wybierz tabelę:", JLabel.CENTER));
        toolsPanel.add(buttonPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(toolsPanel);
        add(mainPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno tabel - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
