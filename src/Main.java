import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Главный класс приложения генерации случайного рисунка.
 * Создает окно, элементы управления и запускает генерацию фигур.
 */
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("START MAIN");
        LOGGER.info("Application started");
        SwingUtilities.invokeLater(Main::createAndShowGui);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Генерация случайного рисунка");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DrawPanel drawPanel = new DrawPanel();

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
                InputParameters params = new InputParameters(
                        Integer.parseInt(tfLines.getText()),
                        Integer.parseInt(tfCircles.getText()),
                        Integer.parseInt(tfRects.getText()),
                        Integer.parseInt(tfTriangles.getText()),
                        Integer.parseInt(tfParabolas.getText()),
                        Integer.parseInt(tfTrapezoids.getText()),
                        Double.parseDouble(tfMinX.getText()),
                        Double.parseDouble(tfMaxX.getText()),
                        Double.parseDouble(tfMinY.getText()),
                        Double.parseDouble(tfMaxY.getText()),
                        Double.parseDouble(tfDensity.getText()),
                        Double.parseDouble(tfGridStep.getText())
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
