package tables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

/**
 * To podejście NIE JEST zalecane. Polecam skorzystać przynajmniej z TableTwo
 */
public class TableOne extends JFrame {
    private String[] columnNames = {"Planet", "Radius", "Moons", "Gaseous", "Color"};
    private Object[][] cells = {
            {"Mercury", 2440.0, 0, false, Color.YELLOW},
            {"Venus", 6052.0, 0, false, Color.YELLOW },
            {"Earth", 6378.0, 1, false, Color.BLUE},
            {"Mars", 3397.0, 2, false, Color.RED},
            {"Jupiter", 71492.0, 16, true, Color.ORANGE},
            {"Your mother", 999999.0, 999, true, Color.BLACK},
            {"Saturn", 60268.0, 18, true, Color.ORANGE},
            {"Uranus", 25559.0, 17, true, Color.BLUE},
            {"Neptune", 24766.0, 8, true, Color.BLUE},
            {"Pluto", 1137.0, 1, false, Color.BLACK}
    };

    public TableOne() {
        /*
         * Z docs.oracle.com:
         * JTable(int numRows, int numColumns)
         * Constructs a JTable with numRows and numColumns of empty cells using DefaultTableModel.
         */
        final JTable table = new JTable(cells, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        add(scrollPane, BorderLayout.CENTER);

        JButton printButton = new JButton("Print");
        printButton.addActionListener(e -> EventHandler.create(ActionListener.class, table, "print"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(900, 250));
        setTitle("Tabela 1 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }
}
