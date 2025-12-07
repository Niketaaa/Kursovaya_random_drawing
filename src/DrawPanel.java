import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Панель, на которой рисуется координатная сетка и все сгенерированные фигуры.
 */
public class DrawPanel extends JPanel {

    private final List<DrawableShape> shapes = new ArrayList<>();
    private double minX = 0;
    private double maxX = 800;
    private double minY = 0;
    private double maxY = 600;
    private double gridStep = 0;

    private static final int MARGIN = 20;

    public DrawPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800 + 2 * MARGIN, 600 + 2 * MARGIN));
    }

    public void setParameters(double minX, double maxX, double minY, double maxY, double gridStep) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.gridStep = gridStep;
    }

    public void setShapes(List<DrawableShape> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(MARGIN, MARGIN);

        drawGrid(g2);
        for (DrawableShape s : shapes) {
            s.draw(g2);
        }
        g2.dispose();
    }

    private void drawGrid(Graphics2D g2) {
        if (gridStep <= 0) {
            return;
        }
        g2.setColor(Color.LIGHT_GRAY);
        for (double x = minX; x <= maxX; x += gridStep) {
            int px = (int) x;
            g2.drawLine(px, (int) minY, px, (int) maxY);
        }
        for (double y = minY; y <= maxY; y += gridStep) {
            int py = (int) y;
            g2.drawLine((int) minX, py, (int) maxX, py);
        }
    }
}
