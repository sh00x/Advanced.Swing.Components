package additionalResources;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.lang.reflect.Modifier;

/**
 * Created by Ensies on 2015-08-03.
 */
public class ClassNameTreeCellRenderer extends DefaultTreeCellRenderer {
    private Font plainFont = null;
    private Font italicFont = null;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        //Pobiera obiekt u�ytkownika
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Class<?> c = (Class<?>) node.getUserObject();

        //Przy pierwszym uzyciu tworzy czcionke pochy�a odpowiadajaca danej czcione prostej
        if (plainFont == null) {
            plainFont = getFont();
            //Obiekt rysuj�cy kom�rk� drzewa wywo�any jest czasami
            //dla etykiety, kt�ra nie posiada okre�lonej czcionki (null)

            if (plainFont != null) italicFont = plainFont.deriveFont(Font.ITALIC);
        }

        //Wybiera czcionk� pochy��, je�li klasa jest abstrakcyjna
        if ((c.getModifiers() & Modifier.ABSTRACT) == 0) setFont(plainFont);
        else setFont(italicFont);
        return this;
    }
}