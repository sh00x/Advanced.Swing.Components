package clipboard;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

/**
 * Ramka zawierjąca komponent wyboru kolorów
 * oraz przyciski operacji kopiowania i wklejania
 */
public class JavaClipboardWindow extends JFrame {
    private JColorChooser colorChooser;

    public JavaClipboardWindow() {
        colorChooser = new JColorChooser();
        add(colorChooser, BorderLayout.CENTER);
        JPanel panel = new JPanel();

        JButton copyButton = new JButton("Copy");
        panel.add(copyButton);
        copyButton.addActionListener(e -> copy());

        JButton pasteButton = new JButton("Paste");
        panel.add(pasteButton);
        pasteButton.addActionListener(e -> paste());
        add(panel, BorderLayout.SOUTH);

        setTitle("Java items I/O clipboard - @sh00x.dev");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Kopiuje wybrany kolor do schowka systemowego
     */
    private void copy() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Color color = colorChooser.getColor();
        SerialTransferable selection = new SerialTransferable(color);
        clipboard.setContents(selection, null);
    }

    /**
     * Wkleja kolor ze schowka systemowego do komponentu wyboru koloru
     */
    private void paste() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            DataFlavor flavor = new DataFlavor("application/x-java-serialized-object;class=java.awt.Color");    //TODO: check out this, MIME
            if (clipboard.isDataFlavorAvailable(flavor)) {
                Color color = (Color) clipboard.getData(flavor);
                colorChooser.setColor(color);
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e);
        } catch (UnsupportedFlavorException e) {
            JOptionPane.showMessageDialog(this, e);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    /**
     * Klasa obudowująca serializowane obiekty
     * przekazywane za pomocą schowka systemowego
     * (w schowku jest przechowywane w postaci binarnej)
     */
    private class SerialTransferable implements Transferable {
        private Serializable obj;

        SerialTransferable(Serializable obj) {
            this.obj = obj;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[2];
            Class<?> type = obj.getClass();
            String mimeType = "application/x-java-serialized-object;class=" + type.getName();
            try {
                flavors[0] = new DataFlavor(mimeType);
                flavors[1] = DataFlavor.stringFlavor;
                return flavors;
            } catch (ClassNotFoundException ex) {
                return new DataFlavor[0];
            }
        }

        /**
         * Java doc: Returns whether or not the specified data flavor is supported for this object.
         *
         * @param flavor the requested flavor for the data
         * @return boolean indicating whether or not the data flavor is supported
         */
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.stringFlavor.equals(flavor) ||
                    "application".equals(flavor.getPrimaryType())
                            && "x-java-serialized-object".equals(flavor.getSubType())
                            && flavor.getRepresentationClass().isAssignableFrom(obj.getClass());
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) throw new UnsupportedFlavorException(flavor);
            if (DataFlavor.stringFlavor.equals(flavor)) return obj.toString();
            return obj;
        }
    }
}

