package clipboard;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Program demonsturjący podstawowa obsługę
 * mechanizmu "przeciągnij i upuść" przez
 * komponenty Swing
 */
public class DragAndDrop extends JFrame {

    public DragAndDrop() {
        setTitle("Przeciągnij i upuść");
        JTabbedPane tabbedPane = new JTabbedPane();

        JList list = null;
        try {
            list = SampleComponents.list();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JScrollPane listScrollPane = new JScrollPane(list);
        tabbedPane.addTab("List", listScrollPane);

        JTable table = SampleComponents.table();
        JScrollPane jScrollPane = new JScrollPane(table);
        tabbedPane.addTab("Table", jScrollPane);

        JTree tree = SampleComponents.tree();
        tabbedPane.addTab("Tree", tree);

        JFileChooser fileChooser = new JFileChooser();
        tabbedPane.addTab("File Chooser", fileChooser);

        JColorChooser colorChooser = new JColorChooser();
        tabbedPane.addTab("Color Chooser", colorChooser);

        final JTextArea textArea = new JTextArea(4, 40);
        textArea.setFont(new Font("System", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Drag text here"));

        JTextField textField = new JTextField("Drag color here");
        textField.setTransferHandler(new TransferHandler("background"));
        textField.setHorizontalAlignment(JTextField.CENTER);

        tabbedPane.addChangeListener(e -> textArea.setText(""));
        tree.setDragEnabled(true);
        table.setDragEnabled(true);
        table.setAutoCreateRowSorter(true);
        if (list != null) list.setDragEnabled(true);
        fileChooser.setDragEnabled(true);
        colorChooser.setDragEnabled(true);
        textField.setDragEnabled(true);
        textField.setEditable(false);

        add(tabbedPane, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
}
