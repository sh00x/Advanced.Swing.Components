package printing;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Panel tworzący grafikę 2D na potrzeby prezentacji na ekranie i wydruku
 */
public class PrintComponent extends JComponent implements Printable {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawPage(g2);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) return Printable.NO_SUCH_PAGE;
        Graphics2D g2 = (Graphics2D) graphics;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2.draw(new Rectangle2D.Double(0, 0, pageFormat.getImageableX(), pageFormat.getImageableHeight()));
        drawPage(g2);
        return Printable.PAGE_EXISTS;
    }

    /**
     * Metoda tworząca obraz strony, wykorzystująca kontekst
     * graficzny ekranu oraz drukarki
     *
     * @param g2 kontekt graficzny
     */
    public void drawPage(Graphics2D g2) {
        FontRenderContext context = g2.getFontRenderContext();
        Font f = new Font("System", Font.PLAIN, 72);
        GeneralPath clipShape = new GeneralPath();

        TextLayout layout = new TextLayout("Hello", f, context);
        AffineTransform transform = AffineTransform.getTranslateInstance(0, 72);
        Shape outline = layout.getOutline(transform);
        clipShape.append(outline, false);

        layout = new TextLayout("World", f, context);
        transform = AffineTransform.getTranslateInstance(0, 144);
        outline = layout.getOutline(transform);
        clipShape.append(outline, false);

        g2.draw(clipShape);
        g2.draw(clipShape);

        final int NLINES = 50;
        Point2D p = new Point2D.Double(0, 0);
        for (int i = 0; i < NLINES; i++) {
            double x = (2 * getWidth() * i) / NLINES;
            double y = (2 * getHeight() * (NLINES - 1 - i)) / NLINES;
            Point2D q = new Point2D.Double(x, y);
            g2.draw(new Line2D.Double(p, q));
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
