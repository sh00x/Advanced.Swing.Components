package app;

import advancedComponents.MainFrame;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new MainFrame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
