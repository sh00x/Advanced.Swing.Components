package textComponents;

import additionalResources.IPAddressFormatter;
import additionalResources.IntFilter;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * Ramka zawierająca kolekcję soframtowanych pól tekstowych
 * oraz przycisk wyświetlający ich wartości.
 */
public class CompTwo extends JFrame {
    private DocumentFilter filter = new IntFilter();
    private JButton okButton;
    private JPanel mainPanel;

    public CompTwo() {
        JPanel buttonPanel = new JPanel();
        okButton = new JButton("Ok");
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 3));
        add(mainPanel, BorderLayout.CENTER);

        JFormattedTextField intField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        intField.setHorizontalAlignment(JFormattedTextField.CENTER);
        intField.setValue(new Integer(100));
        addRow("Liczba:", intField);

        JFormattedTextField intFieldTwo = new JFormattedTextField(NumberFormat.getIntegerInstance());
        intFieldTwo.setValue(new Integer(100));
        intFieldTwo.setHorizontalAlignment(JFormattedTextField.CENTER);
        //JFormattedTextField.COMMIT - Jeśli łańcuch jest niepoprawny, to tekst pola i
        //jego wartość pozostają niezmienione, czyli utracona zostaje ich synchronizacja
        intFieldTwo.setFocusLostBehavior(JFormattedTextField.COMMIT);
        addRow("Liczba (Commit behavior):", intFieldTwo);

        //Pole z zainstalowanym filtrem - uniemożliwa wprowadzanie znaków które nie są cyframi
        JFormattedTextField intFieldThree = new JFormattedTextField(new InternationalFormatter(NumberFormat.getIntegerInstance())) {
            protected DocumentFilter getDocumentFilter() {
                return filter;
            }
        };
        intFieldThree.setValue(new Integer(100));
        intFieldThree.setHorizontalAlignment(JFormattedTextField.CENTER);
        addRow("Filtrowana liczba:", intFieldThree);

        //Pole z zainstalowanym weryfikatorem - wkracza on do akcji gdy użytkownik opuszcza pole tekstowe.
        //Jeśli uzna wartość pola za niepoprawną, kursor wraca natychmiast do danego pola tekstowego i
        //użytkownik zostaje w ten sposób zmuszony do poprawienia wprowadzonej wartości
        JFormattedTextField intFieldFour = new JFormattedTextField(NumberFormat.getIntegerInstance());
        intFieldFour.setHorizontalAlignment(JFormattedTextField.CENTER);
        intFieldFour.setValue(new Integer(100));
        intFieldFour.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JFormattedTextField field = (JFormattedTextField) input;
                return field.isEditValid();
            }
        });
        addRow("Weryfikowana liczba:", intFieldFour);

        //Pole waluty
        JFormattedTextField currencyField = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        currencyField.setHorizontalAlignment(JFormattedTextField.CENTER);
        currencyField.setValue(new Double(10));
        addRow("Waluta:", currencyField);

        //Pole daty
        JFormattedTextField dateField = new JFormattedTextField(DateFormat.getDateInstance());
        dateField.setHorizontalAlignment(JFormattedTextField.CENTER);
        dateField.setValue(new Date());
        addRow("Data (domyślna):", dateField);

        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        format.setLenient(false);
        JFormattedTextField dateFieldTwo = new JFormattedTextField(format);
        dateFieldTwo.setHorizontalAlignment(JFormattedTextField.CENTER);
        dateFieldTwo.setValue(new Date());
        addRow("Data (krótka, nie lenient):", dateFieldTwo);

        try {
            //DefaultFormatter - służy do foramtowania obiektów URL
            DefaultFormatter formatter = new DefaultFormatter();
            formatter.setOverwriteMode(false);
            JFormattedTextField urlField = new JFormattedTextField(formatter);
            urlField.setHorizontalAlignment(JFormattedTextField.CENTER);
            urlField.setValue(new URL("http://google.com"));
            addRow("URL:", urlField);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Genialne!
            //# - cyfra, U - litera
            MaskFormatter formatter = new MaskFormatter("###-##-####");
            formatter.setPlaceholderCharacter('0');
            JFormattedTextField ssnField = new JFormattedTextField(formatter);
            ssnField.setHorizontalAlignment(JFormattedTextField.CENTER);
            ssnField.setValue("078-05-1120");
            addRow("Maska SSN:", ssnField);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //IPAddressFormatter rozszerza DefaultFormatter - co umożliwia nam zastosowanie
        //własnego obiektu formatującego. Należy nadpisać valueToString(Object value)
        //oraz stringToValue(String text)
        JFormattedTextField ipField = new JFormattedTextField(new IPAddressFormatter());
        ipField.setHorizontalAlignment(JFormattedTextField.CENTER);
        ipField.setValue(new byte[]{(byte) 130, 65, 86, 66});
        addRow("Adres IP: ", ipField);

        setTitle("Okno komponentów tekstowych 2 - @sh00x.dev");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(700, 400));
        setVisible(true);
        pack();
    }

    /**
     * Dodaje wiersz w głównym panelu.
     *
     * @param labelText etykieta pola,
     * @param field     przykładowe pole
     */
    public void addRow(String labelText, final JFormattedTextField field) {
        mainPanel.add(new JLabel(labelText, JLabel.RIGHT));
        mainPanel.add(field);
        final JLabel valueLabel = new JLabel();
        mainPanel.add(valueLabel);
        okButton.addActionListener(e -> {
            Object value = field.getValue();
            Class<?> cl = value.getClass();
            String text = null;
            if (cl.isArray()) {
                if (cl.getComponentType().isPrimitive()) {
                    try {
                        text = Arrays.class.getMethod("toString", cl).invoke(null, value).toString();
                    } catch (ReflectiveOperationException ex) {
                        //ignoruje wyjątki refleksji
                    }
                } else text = Arrays.toString((Object[]) value);
            } else text = value.toString();
            valueLabel.setText(text);
        });
    }
}
