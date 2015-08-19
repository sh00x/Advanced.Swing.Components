package printing;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * Ramka zawierająca panel z grafiką 2D
 * i przyciski umożliwiające wydruk grafiki
 * oraz określenie formatu strony
 */
public class PrintingWindow extends JFrame {
    private PrintComponent canvas;
    private PrintRequestAttributeSet attributes;

    public PrintingWindow() {
        canvas = new PrintComponent();
        add(canvas, BorderLayout.CENTER);

        attributes = new HashPrintRequestAttributeSet();

        JPanel buttonPanel = new JPanel();
        JButton printButton = new JButton("Print");
        buttonPanel.add(printButton);
        printButton.addActionListener(e -> {
            try {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(canvas);
                if (job.printDialog(attributes)) job.print(attributes);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(PrintingWindow.this, ex);
            }
        });

        JButton pageSetupButton = new JButton("Page setup");
        buttonPanel.add(pageSetupButton);
        pageSetupButton.addActionListener(e -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pageFormat = job.pageDialog(job.defaultPage());  //Dodaje podgląd strony
            //job.pageDialog(attributes);
        });

        add(buttonPanel, BorderLayout.NORTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Printing Window 1 - @sh00x.dev");
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
