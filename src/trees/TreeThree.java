package trees;

import additionalResources.ClassNameTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

/**
 * Created by Ensies on 2015-08-03.
 */
public class TreeThree extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private JTree tree;
    private JTextField textField;
    private JTextArea textArea;

    public TreeThree() {
        //W korzeniu drzewa znajduje się klasa Object, można tam dać cokolwiek
        root = new DefaultMutableTreeNode(java.lang.Object.class);
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        //Dodaje klasę do drzewa
        addClass(getClass());

        //Tutaj można wstawić ikony zamkniętych oraz otwartych węzłów, a także liści
        // ClassNameTreeCellRenderer renderer = new ClassNameTreeCellRenderer();
        // renderer.setClosedIcon(new ImageIcon(getClass().getResource(/*"red-ball.gif"*/)));
        // renderer.setOpenIcon(new ImageIcon(getClass().getResource(/*"yellow-ball.gif"*/)));
        // renderer.setLeafIcon(new ImageIcon(getClass().getResource(/*"blue-ball.gif"*/)));

        //Konfiguruje sposób wyboru węzłów
        tree.addTreeSelectionListener(e -> {
            //Użytkownik wybrał inny węzeł drzewa i należy zaktualizować opis klasy
            TreePath path = tree.getSelectionPath();
            if (path == null) return;
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            Class<?> c = (Class<?>) selectedNode.getUserObject();
            String description = getFieldDescription(c);
            textArea.setText(description);
        });
        int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
        tree.getSelectionModel().setSelectionMode(mode);

        //Obszar tekstowy zawierający opis klasy
        textArea = new JTextArea();

        //Dodaje komponenty drzewa i pola tekstowego do panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JScrollPane(tree));
        panel.add(new JScrollPane(textArea));

        add(panel, BorderLayout.CENTER);

        addTextField();

        //Konfiguracja fizycznych właściwości okna
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setTitle("Drzewo 3 - @sh00x.dev");
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }

    /**
     * Dodaje pole tekstowe i przycisk "Add"
     * umożliwiając dodanie nowej klasy do drzewa
     *
     */
    public void addTextField() {
        JPanel panel = new JPanel();
        ActionListener addListener = e -> {
            //Dodaje do drzewa klasę, której nazwa znajduje sie w polu tekstowym
            try {
                String text = textField.getText();
                addClass(Class.forName(text)); //opróżnia zawartość
                textField.setText("");
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "Class not found");
            }
        };

        //pole tekstowe, w którym wprowadzane są nazwy nowych klas
        textField = new JTextField(20);
        textField.addActionListener(addListener);
        panel.add(textField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(addListener);
        panel.add(addButton);

        add(panel, BorderLayout.SOUTH);
    }

    /**
     * Wyszukuję obiekt w drzewie.
     * @param obj szukany obiekt
     * @return węzeł zawierający szukany obiekt lub null,
     * jeśli obiekt nie znajduje się w drzewie
     */
    @SuppressWarnings("unchecked")
    public DefaultMutableTreeNode findUserObject(Object obj) {
        //Szuka węzła zawierającego obiekt użytkownika
        Enumeration<TreeNode> e = (Enumeration<TreeNode>) root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if(node.getUserObject().equals(obj)) return node;
        }
        return null;
    }

    /**
     * Dodaje do drzewa klasę i jej klasy bazowe,
     * których nie ma jeszcze w drzewie
     * @param c dodawana klasa
     * @return nowo dodany węzeł
     */
    public DefaultMutableTreeNode addClass(Class<?> c) {
        //Pomija typy, które nie są klasami
        if (c.isInterface() || c.isPrimitive()) return null;

        //Jeśli klasa znajduje się w drzewie, to najpierw należy dodać rekrurencyjnie do drzewa jej klasy bazowe
        //Po ludzku: Wpisujesz coś do textField, on sprawdza czy ta klasa ma nadklasę. Jeśli tak, metoda leci od
        //nowa, aż do momentu gdy nie będzie nic wyżej. Jak już dotrze na sam szczyt, przypisuję wartość korzenia
        //i przechodzi dalej do tworzenia węzłów
        Class<?> s = c.getSuperclass();
        DefaultMutableTreeNode parent;
        if (s == null) parent = root;
        else parent = addClass(s);

        //Dodaje klasę jako węzeł podrzędny
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(c);
        model.insertNodeInto(newNode, parent, parent.getChildCount());

        //Sprawia, że węzeł jest widoczny
        TreePath path = new TreePath(model.getPathToRoot(newNode));
        tree.makeVisible(path);

        return newNode;
    }

    /**
     * Zwraca opis składowych klasy,
     * @return łańcuch znaków zawierający nazwy i typy zmiennych
     */
    public static String getFieldDescription(Class<?> c) {
        //Korzysta z mechanizmu reflekcji
        StringBuilder r = new StringBuilder();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if((f.getModifiers() & Modifier.STATIC) != 0) r.append("static ");
            r.append(f.getType().getName());
            r.append(" ");
            r.append(f.getName());
            r.append("\n");
        }
        return r.toString();
    }
}
