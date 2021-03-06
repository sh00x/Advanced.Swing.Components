package tables;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;


public class TableThree extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 500;

    public static final int COLOR_COLUMN = 4;
    public static final int IMAGE_COLUMN = 5;

    private JTable table;
    private HashSet<Integer> removedRowIndices;
    private ArrayList<TableColumn> removedColumns;
    private JCheckBoxMenuItem rowsItem;
    private JCheckBoxMenuItem columnsItem;
    private JCheckBoxMenuItem cellsItem;
    private ImageIcon planetIcon = new ImageIcon("src/files/planet.png");
    private String[] columnNames = {"Planet", "Radius", "Moons", "Gaseous", "Color", "Image"};

    private Object[][] cells = {
            {"Mercury", 2440.0, 0, false, Color.YELLOW, planetIcon},
            {"Venus", 6052.0, 0, false, Color.YELLOW, planetIcon},
            {"Earth", 6378.0, 1, false, Color.BLUE, planetIcon},
            {"Mars", 3397.0, 2, false, Color.RED, planetIcon},
            {"Jupiter", 71492.0, 16, true, Color.ORANGE, planetIcon},
            {"Your mother", 999999.0, 999, true, Color.BLACK, planetIcon},
            {"Saturn", 60268.0, 18, true, Color.ORANGE, planetIcon},
            {"Uranus", 25559.0, 17, true, Color.BLUE, planetIcon},
            {"Neptune", 24766.0, 8, true, Color.BLUE, planetIcon},
            {"Pluto", 1137.0, 1, false, Color.BLACK, planetIcon}
    };

    public TableThree() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        TableModel model = new DefaultTableModel(cells, columnNames) {
            public Class<?> getColumnClass(int c) {
                return cells[0][c].getClass();
            }
        };

        table = new JTable(model);

        table.setRowHeight(100);
        table.getColumnModel().getColumn(COLOR_COLUMN).setMinWidth(250);
        table.getColumnModel().getColumn(IMAGE_COLUMN).setMinWidth(100);

        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setComparator(COLOR_COLUMN, new Comparator<Color>() {
            @Override
            public int compare(Color c1, Color c2) {
                int d = c1.getBlue() - c2.getBlue();
                if (d != 0) return d;
                d = c1.getGreen() - c2.getGreen();
                if (d != 0) return d;
                return c1.getRed() - c2.getRed();
            }
        });
        sorter.setSortable(IMAGE_COLUMN, false);
        add(new JScrollPane(table), BorderLayout.CENTER);

        removedRowIndices = new HashSet<>();
        removedColumns = new ArrayList<>();

        final RowFilter<TableModel, Integer> filter = new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                return !removedRowIndices.contains(entry.getIdentifier());
            }
        };

        //Tworzy menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu selectionMenu = new JMenu("Selection");
        menuBar.add(selectionMenu);

        rowsItem = new JCheckBoxMenuItem("Rows");
        columnsItem = new JCheckBoxMenuItem("Columns");
        cellsItem = new JCheckBoxMenuItem("Cells");

        rowsItem.setSelected(table.getRowSelectionAllowed());

        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());

        rowsItem.addActionListener(e -> {
            table.clearSelection();
            table.setRowSelectionAllowed(rowsItem.isSelected());
            updateCheckboxMenuItems();
        });
        selectionMenu.add(rowsItem);

        columnsItem.addActionListener(e -> {
            table.clearSelection();
            table.setColumnSelectionAllowed(columnsItem.isSelected());
            updateCheckboxMenuItems();
        });
        selectionMenu.add(columnsItem);

        cellsItem.addActionListener(e -> {
            table.clearSelection();
            table.setCellSelectionEnabled(cellsItem.isSelected());
            updateCheckboxMenuItems();
        });
        selectionMenu.add(cellsItem);

        JMenu tableMenu = new JMenu("Edit");
        menuBar.add(tableMenu);

        JMenuItem hideColumnsItem = new JMenuItem("Hide Columns");
        hideColumnsItem.addActionListener(e -> {
            int[] selected = table.getSelectedColumns();
            TableColumnModel columnModel = table.getColumnModel();

            //Usuwa kolumny z wiodku tabeli, pocz�wszy od
            //najwy�szego indeksu, aby nie zmienia� numer�w kolumn
            for (int i = selected.length - 1; i >= 0; i--) {
                TableColumn column = columnModel.getColumn(selected[i]);
                table.removeColumn(column);

                //przechowuje ukryte kolumny do ponownej prezentacji
                removedColumns.add(column);
            }
        });
        tableMenu.add(hideColumnsItem);

        JMenuItem showColumnsItem = new JMenuItem("Show Columns");
        showColumnsItem.addActionListener(e -> {
            //Przywrca wszystkie usuni�te kolumny
            for(TableColumn tc : removedColumns)
                table.addColumn(tc);
            removedColumns.clear();
        });
        tableMenu.add(showColumnsItem);

        JMenuItem hideRowsItem = new JMenuItem("Hide Rows");

        hideRowsItem.addActionListener(e -> {
            int[] selected = table.getSelectedRows();
            for (int i : selected)
                removedRowIndices.add(table.convertRowIndexToModel(i));
            sorter.setRowFilter(filter);
        });
        tableMenu.add(hideRowsItem);

        JMenuItem printSelectionItem = new JMenuItem("Print Selection");
        printSelectionItem.addActionListener(e -> {
            int[] selected = table.getSelectedRows();
            System.out.println("Selected rows: " + Arrays.toString(selected));
            selected = table.getSelectedColumns();
            System.out.println("Selected columns: " + Arrays.toString(selected));
        });
        tableMenu.add(printSelectionItem);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tabela 3 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }

    private void updateCheckboxMenuItems() {
        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());
    }
}


