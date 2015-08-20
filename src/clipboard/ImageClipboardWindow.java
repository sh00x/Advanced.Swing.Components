package clipboard;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Ramka zawieraj¹ca
 */
public class ImageClipboardWindow extends JFrame {
    private JLabel label;
    private Image image;

    public ImageClipboardWindow() {
        label = new JLabel();
        image = new ImageIcon("src/files/images/addImageClip_10.png").getImage();

        label.setIcon(new ImageIcon(image));
        add(new JScrollPane(label), BorderLayout.CENTER);
        JPanel panel = new JPanel();

        JButton copyButton = new JButton("Copy");
        panel.add(copyButton);
        copyButton.addActionListener(e -> copy());

        JButton pasteButton = new JButton("Paste");
        panel.add(pasteButton);
        pasteButton.addActionListener(e -> paste());
        add(panel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Image to clipboard");
        pack();
        setVisible(true);
    }

    /**
     * Kopiuje bie¿acy obraz do schowka systemowego
     */
    private void copy() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        ImageTransferable selection = new ImageTransferable(image);
        clipboard.setContents(selection, null);
    }

    /**
     * Wkleja obraz ze schowka systemowego
     */
    private void paste() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.imageFlavor;
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                image = (Image) clipboard.getData(flavor);
                label.setIcon(new ImageIcon(image));
            } catch (UnsupportedFlavorException ex) {
                JOptionPane.showMessageDialog(this, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }

    private class ImageTransferable implements Transferable {
        private Image image;

        public ImageTransferable(Image image) {
            this.image = image;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{
                    DataFlavor.imageFlavor
            };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.imageFlavor);
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (flavor.equals(DataFlavor.imageFlavor)) {
                return image;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }
}
