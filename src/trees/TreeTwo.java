package trees;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public class TreeTwo extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 200;

    DefaultMutableTreeNode root;
    DefaultMutableTreeNode country;
    DefaultMutableTreeNode state;
    DefaultMutableTreeNode city;

    private DefaultTreeModel model;
    private JTree tree;

    public TreeTwo() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //Tworzy drzewo
        TreeNode root = makeSampleTree();
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
        tree.setEditable(true);

        //Umieszcza drzewo w przewijalnym panelu
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        makeButtons();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setTitle("Drzewo 2 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }

    public TreeNode makeSampleTree() {
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

        return root;
    }

    /**
     * Tworzy przyciski umo¿liwiaj¹ce dodanie wêz³a siostrzanego,
     * dodanie we¿³a podrzêdnego oraz usuniêcie wybranego wêz³a
     */
    public void makeButtons() {
        JPanel panel = new JPanel();
        JButton addSiblingButton = new JButton("Add Sibling");

        addSiblingButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) return;

            //Sprawia, ¿e nie mo¿na dodaæ np. World 2
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
            if (parent == null) return;

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New");

            int selectedIndex = parent.getIndex(selectedNode);
            model.insertNodeInto(newNode, parent, selectedIndex + 1);

            //Wyœwietla nowy wêze³
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.scrollPathToVisible(path);
        });
        panel.add(addSiblingButton);

        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) return;

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New");
            model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());

            //Wyœwietla nowy wêze³
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.scrollPathToVisible(path);
        });
        panel.add(addChildButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if(selectedNode != null && selectedNode.getParent() != null) model.removeNodeFromParent(selectedNode);
        });
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);
    }
}
