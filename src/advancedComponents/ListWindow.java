package advancedComponents;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by sh00x.dev
 * Simple application with Advanced Swing Components
 *
 * @author sh00x.dev
 */
public class ListWindow extends JFrame {
    private JList<String> list;
    private DefaultListModel<String> model;
    private JTextField textField;
    private JLabel selectedTextLabel;

    private JPanel northPanel;
    private JPanel radioPanel;

    private ButtonGroup radioButtonGroup;

    public ListWindow() {
        model = new DefaultListModel<>();

        for (int i = 1; i <= 10; i++)
            model.addElement("Example String number: " + i);

        list = new JList<>(model);
        list.setPrototypeCellValue("WWWWWWWWWWWWWWWWWWWW");
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        JScrollPane listScrollPane = new JScrollPane(list);
        selectedTextLabel = new JLabel(list.getSelectedValue());
        selectedTextLabel.setHorizontalAlignment(JLabel.CENTER);

        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        northPanel.add(listScrollPane);
        northPanel.add(selectedTextLabel);
        northPanel.setBorder(createTitledBorder("Dodawanie i usuwanie elementów",
                BorderFactory.createEtchedBorder()));

        JButton addButton = new JButton("Dodaj");
        JButton removeButton = new JButton("Skasuj");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JPanel textPanel = new JPanel();
        textField = new JTextField(15);
        textPanel.setLayout(new FlowLayout());
        textPanel.add(textField);

        radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioButtonGroup = new ButtonGroup();

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridLayout(5, 1));
        toolsPanel.add(new JLabel("Wpisz coś i dodaj:", JLabel.CENTER));
        toolsPanel.add(textPanel);
        toolsPanel.add(buttonPanel);
        toolsPanel.add(radioPanel);

        JButton fontCellFrameButton = new JButton("Listing 6.4");
        JPanel listingPanel = new JPanel();
        listingPanel.setLayout(new FlowLayout());
        listingPanel.add(fontCellFrameButton);
        toolsPanel.add(listingPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.CENTER);
        mainPanel.add(toolsPanel, BorderLayout.SOUTH);
        add(mainPanel);

        makeButton("Vertical", JList.VERTICAL);
        makeButton("Vertival Wrap", JList.VERTICAL_WRAP);
        makeButton("Horizontal Wrap", JList.HORIZONTAL_WRAP);

        //Dodanie słuchaczy
        addButton.addActionListener(e -> ListWindow.this.addText());
        removeButton.addActionListener(e -> {
            model.removeElementAt(list.getSelectedIndex());
            list.setSelectedIndex(list.getFirstVisibleIndex());
        });
        list.addListSelectionListener(e -> selectedTextLabel.setText(list.getSelectedValue()));

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addText();
                }
            }
        });

        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    model.removeElementAt(list.getSelectedIndex());
                    list.setSelectedIndex(list.getFirstVisibleIndex());
                }
            }
        });

        fontCellFrameButton.addActionListener(e -> EventQueue.invokeLater(ListRendereingTestFrame::new));

        //Ustawienia fizycznych właściwości okna
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno list - @sh00x.dev");
        setPreferredSize(new Dimension(400, 400));
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }

    /**
     * Tworzy ustaloną TitledBorder
     *
     * @param componentName Nazwa dla ramki
     * @param border        Obiekt typu Border
     * @return TitledBorder z componentName
     */
    private Border createTitledBorder(String componentName, Border border) {
        Font mainFont = new Font("System", Font.PLAIN, 12);
        return BorderFactory.createTitledBorder(border, componentName,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, mainFont);
    }

    /**
     * Dodaje JRadioButton o określonej nazwie i orientacji JList do panelu
     *
     * @param label       Nazwa dla dodawanego RadioButton
     * @param orientation Jedna spośród wartości: JList.VERTICAL, JList.VERTICAL_WRAP, JList.HORIZONTAL_WRAP
     */
    private void makeButton(String label, final int orientation) {
        JRadioButton radioButton = new JRadioButton(label);
        radioPanel.add(radioButton);
        if (radioButtonGroup.getButtonCount() == 0) radioButton.setSelected(true);
        radioButtonGroup.add(radioButton);
        radioButton.addActionListener(e -> {
            list.setLayoutOrientation(orientation);
            northPanel.revalidate();
        });

    }

    /**
     * Prosta metoda dodająca tekst do modelu listy
     */
    private void addText() {
        if (!textField.getText().equals(""))
            model.addElement(textField.getText());
        textField.setText("");
    }
}
