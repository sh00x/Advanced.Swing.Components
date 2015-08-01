package tables;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by Ensies on 2015-07-31.
 */
public class TableTwo extends JFrame {

    public TableTwo() {
        TableModel model = new TableTwoModel(30, 5, 10);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        //Konfiguracja fizycznych właściwości ramki
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tabela 2 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }
}
