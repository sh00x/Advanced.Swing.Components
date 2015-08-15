package appCore;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa main całego programu.
 */
public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new MainWindow();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
