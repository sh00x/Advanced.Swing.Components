package textComponents;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CompOne extends JFrame {
    private JPanel panel;
    private JTextField redField;
    private JTextField greenField;
    private JTextField blueField;

    public CompOne() {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent event) { setColor(); }
            public void removeUpdate(DocumentEvent event) { setColor(); }
            public void changedUpdate(DocumentEvent event) {}
        };

        panel = new JPanel();

        panel.add(new JLabel("Red: "));
        redField = new JTextField("255", 3);
        panel.add(redField);
        redField.getDocument().addDocumentListener(listener);

        panel.add(new JLabel("Green: "));
        greenField = new JTextField("255", 3);
        panel.add(greenField);
        greenField.getDocument().addDocumentListener(listener);

        panel.add(new JLabel("Blue: "));
        blueField = new JTextField("255", 3);
        panel.add(blueField);
        blueField.getDocument().addDocumentListener(listener);

        add(panel);

        //Konfiguracja fizycznych właściwości okna
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno komponentów tekstowych 1 - @sh00x.dev");
        //setPreferredSize(new Dimension(SIZEX, SIZEY));
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     *   Nadaje tłu kolor zgodnie z wartościami pól tekstowych
     */
    public void setColor() {
        try {
            int red = Integer.parseInt(redField.getText().trim());
            int green = Integer.parseInt(greenField.getText().trim());
            int blue = Integer.parseInt(blueField.getText().trim());
            panel.setBackground(new Color(red, green, blue));
        } catch (NumberFormatException e) {
            //nie zmienia koloru, jeśli dane nie mogą być parsowane
        } catch (IllegalArgumentException e) {
            //nie zmienia koloru, jeśli dane nie mogą być parsowane
        }
    }
}