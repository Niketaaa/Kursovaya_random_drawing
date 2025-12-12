import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Главный класс приложения генерации случайного рисунка.
 * Создает главное окно, размещает элементы управления и запускает генерацию фигур.
 */
public class Main {

    /** Логгер приложения для записи служебных сообщений. */
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    /**
     * Точка входа в приложение.
     */
    public static void main(String[] args) {
        System.out.println("START MAIN");
        LOGGER.info("Application started");
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }

    /**
     * Создает и настраивает главное окно приложения.
     * Формирует панели ввода параметров, область рисования и статусную строку.
     */
    private static void createAndShowGui() {
        JFrame frame = new JFrame("Генерация случайного рисунка");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DrawPanel drawPanel = new DrawPanel();

        // количество символов в текстовых полях
        int fieldColumns = 5;

        // ---------- блок количества фигур ----------
        JPanel figuresPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        figuresPanel.setBorder(new TitledBorder("Количество фигур"));

        JTextField tfLines      = new JTextField("5", fieldColumns);
        JTextField tfCircles    = new JTextField("5", fieldColumns);
        JTextField tfRects      = new JTextField("5", fieldColumns);
        JTextField tfTriangles  = new JTextField("5", fieldColumns);
        JTextField tfParabolas  = new JTextField("5", fieldColumns);
        JTextField tfTrapezoids = new JTextField("5", fieldColumns);

        figuresPanel.add(new JLabel("Линии:"));
        figuresPanel.add(tfLines);
        figuresPanel.add(new JLabel("Окружности:"));
        figuresPanel.add(tfCircles);
        figuresPanel.add(new JLabel("Прямоугольники:"));
        figuresPanel.add(tfRects);
        figuresPanel.add(new JLabel("Треугольники:"));
        figuresPanel.add(tfTriangles);
        figuresPanel.add(new JLabel("Параболы:"));
        figuresPanel.add(tfParabolas);
        figuresPanel.add(new JLabel("Трапеции:"));
        figuresPanel.add(tfTrapezoids);

        // ---------- блок области координат ----------
        JPanel areaPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        areaPanel.setBorder(new TitledBorder("Область координат и сетка"));

        JTextField tfMinX     = new JTextField("500", fieldColumns);
        JTextField tfMaxX     = new JTextField("1000", fieldColumns);
        JTextField tfMinY     = new JTextField("0", fieldColumns);
        JTextField tfMaxY     = new JTextField("500", fieldColumns);
        JTextField tfDensity  = new JTextField("0", fieldColumns);
        JTextField tfGridStep = new JTextField("25", fieldColumns);

        tfDensity.setToolTipText("0..1: 0 — равномерно, 1 — больше фигур в центре");
        tfGridStep.setToolTipText("Шаг координатной сетки, 0 — без сетки");

        areaPanel.add(new JLabel("min X:"));
        areaPanel.add(tfMinX);
        areaPanel.add(new JLabel("max X:"));
        areaPanel.add(tfMaxX);
        areaPanel.add(new JLabel("min Y:"));
        areaPanel.add(tfMinY);
        areaPanel.add(new JLabel("max Y:"));
        areaPanel.add(tfMaxY);
        areaPanel.add(new JLabel("Кучность (0..1):"));
        areaPanel.add(tfDensity);
        areaPanel.add(new JLabel("Шаг сетки:"));
        areaPanel.add(tfGridStep);

        // ---------- кнопки ----------
        JButton btnGenerate = new JButton("Сгенерировать");
        JButton btnClear    = new JButton("Очистить");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnClear);
        buttonsPanel.add(btnGenerate);

        // ---------- центрируем блок настроек ----------
        JPanel innerTop = new JPanel(new GridLayout(1, 2, 20, 0));
        innerTop.add(figuresPanel);
        innerTop.add(areaPanel);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(innerTop, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JPanel topWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topWrapper.add(topPanel);

        // ---------- статусная строка ----------
        JLabel statusLabel = new JLabel("Готово.");
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        // ---------- раскладка окна ----------
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(topWrapper, BorderLayout.NORTH);
        frame.add(new JScrollPane(drawPanel), BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);

        // ---------- обработчики кнопок ----------
        btnGenerate.addActionListener(e -> {
            try {
                int lineCount      = Integer.parseInt(tfLines.getText());
                int circleCount    = Integer.parseInt(tfCircles.getText());
                int rectCount      = Integer.parseInt(tfRects.getText());
                int triangleCount  = Integer.parseInt(tfTriangles.getText());
                int parabolaCount  = Integer.parseInt(tfParabolas.getText());
                int trapezoidCount = Integer.parseInt(tfTrapezoids.getText());

                double minX    = Double.parseDouble(tfMinX.getText());
                double maxX    = Double.parseDouble(tfMaxX.getText());
                double minY    = Double.parseDouble(tfMinY.getText());
                double maxY    = Double.parseDouble(tfMaxY.getText());
                double density = Double.parseDouble(tfDensity.getText());
                double grid    = Double.parseDouble(tfGridStep.getText());

                // логическая валидация
                if (minX >= maxX || minY >= maxY) {
                    JOptionPane.showMessageDialog(frame,
                            "Минимальные значения координат должны быть меньше максимальных.",
                            "Ошибка параметров", JOptionPane.ERROR_MESSAGE);
                    LOGGER.warn("Invalid bounds: minX=" + minX + ", maxX=" + maxX +
                            ", minY=" + minY + ", maxY=" + maxY);
                    return;
                }

                if (density < 0 || density > 1) {
                    JOptionPane.showMessageDialog(frame,
                            "Параметр \"Кучность\" должен быть в диапазоне от 0 до 1.",
                            "Ошибка параметров", JOptionPane.ERROR_MESSAGE);
                    LOGGER.warn("Invalid density value: " + density);
                    return;
                }

                if (grid < 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Шаг сетки не может быть отрицательным.",
                            "Ошибка параметров", JOptionPane.ERROR_MESSAGE);
                    LOGGER.warn("Invalid grid step: " + grid);
                    return;
                }

                InputParameters params = new InputParameters(
                        lineCount,
                        circleCount,
                        rectCount,
                        triangleCount,
                        parabolaCount,
                        trapezoidCount,
                        minX,
                        maxX,
                        minY,
                        maxY,
                        density,
                        grid
                );

                LOGGER.info("Drawing generation started");
                RandomShapeGenerator generator = new RandomShapeGenerator();
                generator.generate(params, drawPanel);

                int total = params.getLineCount() + params.getCircleCount()
                        + params.getRectangleCount() + params.getTriangleCount()
                        + params.getParabolaCount() + params.getTrapezoidCount();
                statusLabel.setText("Сгенерировано фигур: " + total);
            } catch (NumberFormatException ex) {
                LOGGER.error("Number input error", ex);
                JOptionPane.showMessageDialog(frame,
                        "Ошибка ввода чисел: " + ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> {
            drawPanel.setShapes(java.util.Collections.emptyList());
            LOGGER.info("Drawing cleared by user");
            statusLabel.setText("Рисунок очищен.");
        });

        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        LOGGER.info("Window shown");
    }
}
