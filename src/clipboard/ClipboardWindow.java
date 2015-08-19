package clipboard;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Ramka zawierająca obszar tekstowy
 * oraz przyciski umożliwiające kopiowane do schowka
 * i wklejanie ze schowka
 */
public class ClipboardWindow extends JFrame {
    private static final int TEXT_ROWS = 10;
    private static final int TEXT_COLUMNS = 30;

    private JTextArea textArea;
    private JButton clipboardContent;

    public ClipboardWindow() {
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        textArea.setFont(new Font("System", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem copyMenuItem = new JMenuItem("Kopiuj");
        JMenuItem pasteMenuItem = new JMenuItem("Wklej");
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        textArea.setComponentPopupMenu(popupMenu);

        copyMenuItem.addActionListener(e -> copy());
        pasteMenuItem.addActionListener(e -> paste());

        clipboardContent = new JButton();
        clipboardContent.setEnabled(false);
        printClipboardContent();

        JButton copyButton = new JButton("Copy");
        panel.add(copyButton);
        copyButton.addActionListener(e -> copy());

        JButton pasteButton = new JButton("Paste");
        panel.add(pasteButton);
        pasteButton.addActionListener(e -> paste());

        setTitle("Clipboard test - @sh00x.dev");
        add(clipboardContent, BorderLayout.NORTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(panel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * Kopiuje wybrany tekst do schowka systemowego
     */
    private void copy() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String text = textArea.getSelectedText();
        if (text == null) text = textArea.getText();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
        printClipboardContent();
    }

    /**
     * Wkleja tekst ze schowka systemowego
     */
    private void paste() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.stringFlavor;    //rodzaj danych
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                String text = (String) clipboard.getData(flavor);
                textArea.replaceSelection(text);
            } catch (UnsupportedFlavorException e) {
                JOptionPane.showMessageDialog(this, e);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }

    /**
     * Ustawia automatycznie wartość JLabel
     * oraz sprawdz czy w schowku już coś jest
     */
    private void printClipboardContent() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.stringFlavor;
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                String text = (String) clipboard.getData(flavor);
                if (text == null || text.equals(""))
                    clipboardContent.setText("Schowek jest pusty");
                else
                    clipboardContent.setText(text);
            } catch (UnsupportedFlavorException e) {
                JOptionPane.showMessageDialog(this, e);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }
}
