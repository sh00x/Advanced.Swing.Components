package textComponents;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

/**
 * Ramka zawierająca panel edytora, pole tekstowe i przycisk Load
 * umożliwiajace wprowadzenie adresu URL i załadowanie strony, a
 * także przycisk Back umożliwiający powrót do poprzedniej strony
 */
public class CompFour extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    public CompFour() {
        final Stack<String> urlStack = new Stack<>();
        final JEditorPane editorPane = new JEditorPane();
        final JTextField urlField = new JTextField(30);

        //Instaluje obiekt nasłuchujący hiperłącza
        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    //Zapamiętuje adres URL dla potrzeb przycisku Back
                    urlStack.push(e.getURL().toString());
                    //Prezentuje adres URL w polu tekstowym
                    urlField.setText(e.getURL().toString());
                    editorPane.setPage(e.getURL());
                } catch (IOException e1) {
                    editorPane.setText("Exception: " + e1);
                }
            }
        });

        //Konfiguruje pole wyboru umożliwiajace właczenie trybu edycji
        final JCheckBox editable = new JCheckBox();
        editable.addActionListener(e -> editorPane.setEditable(editable.isSelected()));

        //Tworzy obiekt nasłuchujący przycisku Load
        ActionListener listener = e -> {
            try {
                //Zapamiętuje adres URL dla potrzeb przycisku Back
                urlStack.push("http://www." + urlField.getText());
                editorPane.setPage("http://www." + urlField.getText());
            } catch (IOException e1) {
                editorPane.setText("Exception: " + e1);
            }
        };

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(listener);
        urlField.addActionListener(listener);

        //Konfiguruje przycisk Back i związaną z nim akcję
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            if (urlStack.size() <= 1) return;
            try {
                //Zapamiętuje adres URL dla potrzeb przycisku Back
                urlStack.pop(); //Wywala to co jest na górze
                //Prezentuje adres URL w polu tekstowym
                String urlString = urlStack.peek(); //Pogląda to co jest na górze
                urlField.setText(urlString);
                editorPane.setPage(urlString);
            } catch (IOException e1) {
                editorPane.setText("Exception: " + e1);
            }
        });

        add(new JScrollPane(editorPane), BorderLayout.CENTER);

        //Umieszcza wszystkie komponenty na głównym panelu okna
        JPanel panel = new JPanel();
        panel.add(new JLabel("URL"));
        panel.add(urlField);
        panel.add(loadButton);
        panel.add(backButton);
        panel.add(new JLabel("Editable"));
        panel.add(editable);

        add(panel, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno komponentów tekstowych - @sh00x.dev");
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }
}
