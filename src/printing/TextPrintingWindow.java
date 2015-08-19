package printing;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * Okno pozwalające na wprowadzenie tekstu i jego wydrukowanie.
 */
public class TextPrintingWindow extends JFrame {
    private JTextArea textArea;
    private PrintRequestAttributeSet attributeSet;

    public TextPrintingWindow() {
        setSize(400, 400);
        textArea = new JTextArea();
        textArea.setFont(new Font("System", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Wprowadź swój tekst"));

        JButton printButton = new JButton("Drukuj");
        JButton pageSetupButton = new JButton("Ustawienia strony");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        buttonPanel.add(pageSetupButton);

        attributeSet = new HashPrintRequestAttributeSet();
        printButton.addActionListener(e -> {
/*            try {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(new TextPrintComponent());
                if (job.printDialog(attributeSet)) job.print(attributeSet);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(TextPrintingWindow.this, ex);
            }*/
            System.out.println(splitText());
        });

        pageSetupButton.addActionListener(e -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pageFormat = job.pageDialog(job.defaultPage());  //Dodaje podgląd strony
        });

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private class TextPrintComponent implements Printable {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2.setFont(new Font("Ubuntu", Font.PLAIN, 8));

            String[] result = splitText().split("\n");
            for (int i = 0, x = 20, y = 20; i < result.length; i++, y = y + 10) {
                g2.drawString(result[i], x, y);
            }
            return Printable.PAGE_EXISTS;
        }
    }

    private String splitText() {
        StringBuilder text = new StringBuilder(textArea.getText());
        int lineHeight = textArea.getFontMetrics(textArea.getFont()).getHeight();
        Point view = new Point(textArea.getWidth(), textArea.getInsets().top);
        int length = textArea.getDocument().getLength();
        int endOfLine = textArea.viewToModel(view);

        int lines = 0;
        while (endOfLine < length) {
            int adjustedEndOfLine = endOfLine + lines;
            if (text.charAt(adjustedEndOfLine) == ' ') {
                text.insert(adjustedEndOfLine + 1, '\n');
                lines++;
            }
            view.y += lineHeight;
            endOfLine = textArea.viewToModel(view);
        }
        System.out.println("length: " + length);
        System.out.println("endOfLine:" + endOfLine);
        System.out.println("lineHeight: " + lineHeight);
        System.out.println("Px: " + view.getX() + ", Py: " + view.getY());
        return text.toString();
    }
}
