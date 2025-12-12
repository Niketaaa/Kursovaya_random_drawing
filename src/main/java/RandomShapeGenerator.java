import org.apache.logging.log4j.LogManager;   // библиотека log4j2: фабрика логгеров
import org.apache.logging.log4j.Logger;       // интерфейс логгера log4j2

import java.awt.*;                           // базовые графические классы AWT
import java.awt.geom.Ellipse2D;              // класс для рисования эллипсов/окружностей
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор случайных фигур по заданным параметрам.
 * Создает коллекцию рисуемых объектов и передает ее панели.
 */
public class RandomShapeGenerator {

    /** Логгер для записи информации о процессе генерации фигур. */
    private static final Logger LOGGER = LogManager.getLogger(RandomShapeGenerator.class);

    /** Источник псевдослучайных чисел для генерации координат и размеров. */
    private final Random random = new Random();

    /**
     * Генерирует набор фигур согласно параметрам и передает их панели.
     * Также записывает в лог состав конфигурации и общее количество созданных фигур.
     * @param params параметры генерации (количество фигур, область, кучность, сетка)
     * @param panel  панель, на которой будут отображены сгенерированные фигуры
     */
    public void generate(InputParameters params, DrawPanel panel) {
        LOGGER.info(
                "Generating shapes: lines=" + params.getLineCount()
                        + ", circles=" + params.getCircleCount()
                        + ", rectangles=" + params.getRectangleCount()
                        + ", triangles=" + params.getTriangleCount()
                        + ", parabolas=" + params.getParabolaCount()
                        + ", trapezoids=" + params.getTrapezoidCount()
        );


        panel.setParameters(
                params.getMinX(), params.getMaxX(),
                params.getMinY(), params.getMaxY(),
                params.getGridStep()
        );
        List<DrawableShape> list = new ArrayList<>();

        addLines(params, list);
        addCircles(params, list);
        addRectangles(params, list);
        addTriangles(params, list);
        addParabolas(params, list);
        addTrapezoids(params, list);

        panel.setShapes(list);
        LOGGER.info("Total shapes created: " + list.size());
    }

    private double randomX(InputParameters p) {
        return biasedRandom(p.getMinX(), p.getMaxX(), p.getDensity());
    }

    private double randomY(InputParameters p) {
        return biasedRandom(p.getMinY(), p.getMaxY(), p.getDensity());
    }

    private double biasedRandom(double min, double max, double density) {
        double center = (min + max) / 2.0;
        double half   = (max - min) / 2.0;
        double r = 2 * random.nextDouble() - 1;
        double factor = 1.0 + density * 4.0;
        r = r / factor;
        return center + r * half;
    }

    private Color randomColor() {
        return new Color(random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256));
    }

    private void addLines(InputParameters p, List<DrawableShape> list) {
        for (int i = 0; i < p.getLineCount(); i++) {
            double x1 = randomX(p);
            double y1 = randomY(p);
            double x2 = randomX(p);
            double y2 = randomY(p);
            Color c = randomColor();
            list.add(g2 -> {
                g2.setColor(c);
                g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            });
        }
    }

    private void addCircles(InputParameters p, List<DrawableShape> list) {
        for (int i = 0; i < p.getCircleCount(); i++) {
            double r = 10 + random.nextDouble() * 40;
            double x = biasedRandom(p.getMinX() + r, p.getMaxX() - r, p.getDensity());
            double y = biasedRandom(p.getMinY() + r, p.getMaxY() - r, p.getDensity());
            Color c = randomColor();
            list.add(g2 -> {
                g2.setColor(c);
                Ellipse2D.Double circle =
                        new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
                g2.draw(circle);
            });
        }
    }

    private void addRectangles(InputParameters p, List<DrawableShape> list) {
        for (int i = 0; i < p.getRectangleCount(); i++) {
            double w = 20 + random.nextDouble() * 60;
            double h = 20 + random.nextDouble() * 60;
            double x = biasedRandom(p.getMinX(), p.getMaxX() - w, p.getDensity());
            double y = biasedRandom(p.getMinY(), p.getMaxY() - h, p.getDensity());
            Color c = randomColor();
            list.add(g2 -> {
                g2.setColor(c);
                g2.drawRect((int) x, (int) y, (int) w, (int) h);
            });
        }
    }

    private void addTriangles(InputParameters p, List<DrawableShape> list) {
        for (int i = 0; i < p.getTriangleCount(); i++) {
            int x1 = (int) randomX(p);
            int y1 = (int) randomY(p);
            int x2 = (int) randomX(p);
            int y2 = (int) randomY(p);
            int x3 = (int) randomX(p);
            int y3 = (int) randomY(p);
            Color c = randomColor();
            list.add(g2 -> {
                g2.setColor(c);
                int[] xs = {x1, x2, x3};
                int[] ys = {y1, y2, y3};
                g2.drawPolygon(xs, ys, 3);
            });
        }
    }

    private void addParabolas(InputParameters p, List<DrawableShape> list) {
        for (int i = 0; i < p.getParabolaCount(); i++) {
            double a = (random.nextDouble() - 0.5) * 0.01;
            double b = (random.nextDouble() - 0.5) * 0.5;
            double c0 = randomY(p);
            Color c = randomColor();

            list.add(g2 -> {
                g2.setColor(c);
                int steps = 40;
                double x0 = p.getMinX();
                double x1 = p.getMaxX();
                int[] xs = new int[steps + 1];
                int[] ys = new int[steps + 1];
                for (int k = 0; k <= steps; k++) {
                    double x = x0 + (x1 - x0) * k / steps;
                    double y = a * x * x + b * x + c0;
                    xs[k] = (int) x;
                    ys[k] = (int) y;
                }
                g2.drawPolyline(xs, ys, steps + 1);
            });
        }
    }

    private void addTrapezoids(InputParameters p, List<DrawableShape> list) {
        for (int i = 0; i < p.getTrapezoidCount(); i++) {
            double bottomWidth = 40 + random.nextDouble() * 60;
            double topWidth    = 20 + random.nextDouble() * bottomWidth;
            double height      = 20 + random.nextDouble() * 40;

            double baseX = biasedRandom(p.getMinX(),
                    p.getMaxX() - bottomWidth, p.getDensity());
            double baseY = biasedRandom(p.getMinY() + height,
                    p.getMaxY(), p.getDensity());

            int x1 = (int) baseX;
            int y1 = (int) baseY;
            int x2 = (int) (baseX + bottomWidth);
            int y2 = (int) baseY;
            int x3 = (int) (baseX + (bottomWidth - topWidth) / 2.0 + topWidth);
            int y3 = (int) (baseY - height);
            int x4 = (int) (baseX + (bottomWidth - topWidth) / 2.0);
            int y4 = (int) (baseY - height);

            Color c = randomColor();
            list.add(g2 -> {
                g2.setColor(c);
                int[] xs = {x1, x2, x3, x4};
                int[] ys = {y1, y2, y3, y4};
                g2.drawPolygon(xs, ys, 4);
            });
        }
    }
}
