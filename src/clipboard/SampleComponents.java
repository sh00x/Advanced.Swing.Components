package clipboard;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Wypełnia elementy
 */
public class SampleComponents {
    public static JTree tree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");

        DefaultMutableTreeNode country = new DefaultMutableTreeNode("USA");
        root.add(country);

        DefaultMutableTreeNode state = new DefaultMutableTreeNode("California");
        country.add(state);

        DefaultMutableTreeNode city = new DefaultMutableTreeNode("San Jose");
        state.add(city);

        city = new DefaultMutableTreeNode("Cupertino");
        state.add(city);

        city = new DefaultMutableTreeNode("Michingan");
        state.add(city);

        city = new DefaultMutableTreeNode("Ann Arbor");
        state.add(city);

        country = new DefaultMutableTreeNode("Germany");
        root.add(country);

        city = new DefaultMutableTreeNode("Berlin");
        country.add(city);

        city = new DefaultMutableTreeNode("Monachium");
        country.add(city);

        return new JTree(root);
    }

    public static JList<String> list() throws IOException {
        ArrayList<String> words = readFile();

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String word : words)
            model.addElement(word);
        return new JList<>(model);
    }

    public static JTable table() {
        Object[][] cells = {
                {"i5-5250U", "3.0 MB", "1.60 GHz", "2/4", "15", "HD Graphics 6000"},
                {"i5-5200U", "3.0 MB", "2.20 GHz", "2/4", "15", "HD Graphics 5500"},
                {"i5-5287U", "3.0 MB", "2.90 GHz", "2/4", "15", "HD Graphics 6100"},
                {"i5-4670K", "6.0 MB", "3.80 GHz", "4/4", "84", "HD Graphics 4600"}
        };

        String[] columnNames = {
                "CPU Name", "L3 Cache", "Frequency", "Number of Cores", "TDP", "IGP"
        };

        return new JTable(cells, columnNames);
    }

    private static ArrayList<String> readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/files/textFiles/keyWordsList.txt"));
        ArrayList<String> tmpWords = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            tmpWords.add(line);
        }

        return tmpWords;
    }
}
