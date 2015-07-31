package appCore;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new MainWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
