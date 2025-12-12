import javax.swing.*;   // стандартная библиотека Swing для создания графического интерфейса
import java.awt.*;      // классы для работы с графикой (цвета, шрифты, Graphics, Graphics2D)
import java.util.ArrayList;
import java.util.List;

/**
 * Панель, на которой рисуется координатная сетка и все сгенерированные фигуры.
 */
public class DrawPanel extends JPanel {

    /** Коллекция фигур, которые нужно отрисовать на панели. */
    private final List<DrawableShape> shapes = new ArrayList<>();
    /** Границы прямоугольной области рисования по оси X. */
    private double minX = 0;
    private double maxX = 800;
    /** Границы прямоугольной области рисования по оси Y. */
    private double minY = 0;
    private double maxY = 600;
    /** Шаг координатной сетки в пикселях. */
    private double gridStep = 0;

    /** Отступ области рисования от краёв панели, в пикселях. */
    private static final int MARGIN = 20;

    /**
     * Создает панель рисования с белым фоном и фиксированным размером.
     */
    public DrawPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(800 + 2 * MARGIN, 600 + 2 * MARGIN));
    }

    /**
     * Устанавливает прямоугольную область координат и шаг сетки.
     */
    public void setParameters(double minX, double maxX, double minY, double maxY, double gridStep) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.gridStep = gridStep;
    }

    /**
     * Заменяет текущий список фигур на новый и инициирует перерисовку панели.
     *
     * @param newShapes коллекция фигур, которые нужно отрисовать
     */
    public void setShapes(List<DrawableShape> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
        repaint();
    }

    /**
     * Переопределенный метод отрисовки Swing.
     */
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

    /**
     * Рисует прямоугольную координатную сетку в заданной области.
     * Используется цвет {@link Color#LIGHT_GRAY}.
     */
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
