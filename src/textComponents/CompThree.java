package textComponents;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Ensies on 2015-08-05.
 */
public class CompThree extends JFrame {
    private JPanel mainPanel;
    private JButton okButton;

    public CompThree() {
        JPanel buttonPanel = new JPanel();
        okButton = new JButton("Ok");
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 3));
        add(mainPanel, BorderLayout.CENTER);

        JSpinner defaultSpinner = new JSpinner();
        addRow("Default", defaultSpinner);

        JSpinner boundedSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 10, 0.5));  //Początkowa wartość 5, krok 0.5, zakres <0, 10>
        addRow("Bounded", boundedSpinner);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        JSpinner listSpinner = new JSpinner(new SpinnerListModel(fonts));
        addRow("List", listSpinner);

        JSpinner reverseListSpinner = new JSpinner(new SpinnerListModel(fonts) {
            public Object getNextValue() { return super.getPreviousValue(); }
            public Object getPreviousValue() { return super.getNextValue(); }
        });
        addRow("Reverse List", reverseListSpinner);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        addRow("Date", dateSpinner);

        JSpinner betterDateSpinner = new JSpinner(new SpinnerDateModel());
        String pattern = ((SimpleDateFormat) DateFormat.getDateInstance()).toPattern();
        betterDateSpinner.setEditor(new JSpinner.DateEditor(betterDateSpinner, pattern));
        addRow("Better Date", betterDateSpinner);

        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        pattern = ((SimpleDateFormat) DateFormat.getTimeInstance(DateFormat.SHORT)).toPattern();
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, pattern));
        addRow("Time", timeSpinner);

        JSpinner permSpinner = new JSpinner(new PermutationSpinnerModel("meat"));
        addRow("Word permutations", permSpinner);

        setTitle("Okno komponentów tekstowych 3 - @sh00x.dev");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setPreferredSize(new Dimension(700, 400));
        setVisible(true);
        pack();
    }

    public void addRow(String labelText, final JSpinner spinner) {
        mainPanel.add(new JLabel(labelText + ": ", JLabel.RIGHT));
        mainPanel.add(spinner);
        final JLabel valueLabel = new JLabel();
        mainPanel.add(valueLabel);
        okButton.addActionListener(e -> {
            Object value = spinner.getValue();
            valueLabel.setText(value.toString());
        });
    }
}
