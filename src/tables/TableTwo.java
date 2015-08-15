package tables;

import javax.swing.*;
import javax.swing.table.TableModel;

public class TableTwo extends JFrame {

    public TableTwo() {
        TableModel model = new TableTwoModel(30, 5, 10);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tabela 2 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }
}
