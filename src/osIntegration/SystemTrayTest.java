package osIntegration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Program demonstrujący wykorzystanie zasobnika systemowego.
 */
public class SystemTrayTest {

    public static void init() {
        final TrayIcon trayIcon;

        if (!SystemTray.isSupported()) {
            System.err.println("System tray is not supported.");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Image image = new ImageIcon("src/files/images/TrollFace.png").getImage();

        PopupMenu popupMenu = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        popupMenu.add(exitItem);

        trayIcon = new TrayIcon(image, "Swing Advanced Components - @shoox.dev", popupMenu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> trayIcon.displayMessage("SAC - Informacja", "RPM -> Exit, aby zamknąć. Zamyka program.", TrayIcon.MessageType.INFO));

        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            System.err.println("Ikona nie mogła zostać dodana.");
            return;
        }

        final List<String> fortunes = readFortunes();
        Timer timer = new Timer(10000, e -> {
            int index = (int) (Math.random() * fortunes.size());
            trayIcon.displayMessage("Swing Advanced Components - @shoox.dev", fortunes.get(index), TrayIcon.MessageType.INFO);
        });
        timer.start();
    }

    private static List<String> readFortunes() {
        List<String> fortunes = new ArrayList<>();
        //Randomowe cytaty z pierwszego linku w google
        fortunes.add("To, że milczę, nie znaczy, że nie mam nic do powiedzenia. - Jonathan Carroll");
        fortunes.add("Lepiej zaliczać się do niektórych, niż do wszystkich. - Andrzej Sapkowski");
        fortunes.add("Czytanie książek to najpiękniejsza zabawa, jaką sobie ludzkość wymyśliła - Wiesława Szymborska");
        fortunes.add("Książki są lustrem: widzisz w nich tylko to co, już masz w sobie. -  Carlos Ruiz Zafón – Cień wiatru");
        fortunes.add("W chwili, kiedy zastanawiasz się czy kogoś kochasz, przestałeś go już kochać na zawsze. - Carlos Ruiz Zafón – Cień wiatru");
        return fortunes;
    }

}
