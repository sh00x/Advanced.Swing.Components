package advancedComponents;

import organizers.OrganizerOne;

import javax.swing.*;
import java.awt.*;

/**
 * Okno z przyciskami otwierającymi okna
 * poszczególnych możliwości organizacji
 * komponentów
 */
public class OrganizationWindow extends JFrame {
    JPanel mainPanel;
    private JButton organizerOne;
    private JButton organizerTwo;
    private JButton organizerThree;

    public OrganizationWindow() {
        setLayout(new GridLayout(2, 1));

        organizerOne = new JButton("Split Pane Frame");
        organizerTwo = new JButton("Organizer 2");
        organizerThree = new JButton("Organizer 3");

        organizerOne.addActionListener(e -> EventQueue.invokeLater(OrganizerOne::new));

        mainPanel = new JPanel();
        mainPanel.add(organizerOne);
        mainPanel.add(organizerTwo);
        mainPanel.add(organizerThree);

        add(new JLabel("Wybierz jedną z opcji:", JLabel.CENTER));
        add(mainPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Okno organiazatorów i dekoratorów - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
