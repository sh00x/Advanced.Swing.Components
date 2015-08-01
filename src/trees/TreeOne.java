package trees;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public class TreeOne extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    DefaultMutableTreeNode root;
    DefaultMutableTreeNode country;
    DefaultMutableTreeNode state;
    DefaultMutableTreeNode city;

    public TreeOne() {
        //Tworzy model drzewa
        root = new DefaultMutableTreeNode("World");

        country = new DefaultMutableTreeNode("USA");
        root.add(country);

        state = new DefaultMutableTreeNode("California");
        country.add(state);

        city = new DefaultMutableTreeNode("San Jose");
        state.add(city);

        city = new DefaultMutableTreeNode("Cupertino");
        state.add(city);

        state = new DefaultMutableTreeNode("Michingan");
        country.add(state);

        city = new DefaultMutableTreeNode("Ann Arbor");
        state.add(city);

        country = new DefaultMutableTreeNode("Germany");
        root.add(country);

        state = new DefaultMutableTreeNode("Schleswig-Holstein");
        country.add(state);

        city = new DefaultMutableTreeNode("Kiel");
        state.add(city);

        country = new DefaultMutableTreeNode("Poland");
        root.add(country);

        state = new DefaultMutableTreeNode("Silesia");
        country.add(state);

        city = new DefaultMutableTreeNode("Katowice");
        state.add(city);

        //Tworzy drzewo i umieszcza je w przewijalnym panelu
        JTree tree = new JTree(root);
        add(new JScrollPane(tree));

        //Konfiguracja fizycznych właściwości okna
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setTitle("Drzewo 1 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }

}
