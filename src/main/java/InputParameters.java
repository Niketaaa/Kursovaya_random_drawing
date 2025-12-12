/**
 * Хранит входные параметры генерации рисунка:
 * количество фигур каждого типа, область координат, кучность и шаг сетки.
 */
public class InputParameters {

    /** Количество случайных линий. */
    private final int lineCount;
    /** Количество окружностей. */
    private final int circleCount;
    /** Количество прямоугольников. */
    private final int rectangleCount;
    /** Количество треугольников. */
    private final int triangleCount;
    /** Количество парабол. */
    private final int parabolaCount;
    /** Количество трапеций. */
    private final int trapezoidCount;

    /** Минимальное значение X области генерации. */
    private final double minX;
    /** Максимальное значение X области генерации. */
    private final double maxX;
    /** Минимальное значение Y области генерации. */
    private final double minY;
    /** Максимальное значение Y области генерации. */
    private final double maxY;

    /** Параметр кучности (0 — равномерно, 1 — максимум в центре). */
    private final double density;
    /** Шаг координатной сетки (0 — без сетки). */
    private final double gridStep;

    /**
     * Создает объект с полным набором параметров генерации.
     *
     * @param lineCount      количество линий
     * @param circleCount    количество окружностей
     * @param rectangleCount количество прямоугольников
     * @param triangleCount  количество треугольников
     * @param parabolaCount  количество парабол
     * @param trapezoidCount количество трапеций
     * @param minX           минимальное значение X
     * @param maxX           максимальное значение X
     * @param minY           минимальное значение Y
     * @param maxY           максимальное значение Y
     * @param density        кучность
     * @param gridStep       шаг координатной сетки
     */
    public InputParameters(int lineCount, int circleCount, int rectangleCount,
                           int triangleCount, int parabolaCount, int trapezoidCount,
                           double minX, double maxX, double minY, double maxY,
                           double density, double gridStep) {
        this.lineCount = lineCount;
        this.circleCount = circleCount;
        this.rectangleCount = rectangleCount;
        this.triangleCount = triangleCount;
        this.parabolaCount = parabolaCount;
        this.trapezoidCount = trapezoidCount;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.density = density;
        this.gridStep = gridStep;
    }

    /** @return количество линий */
    public int getLineCount()      { return lineCount; }
    /** @return количество окружностей */
    public int getCircleCount()    { return circleCount; }
    /** @return количество прямоугольников */
    public int getRectangleCount() { return rectangleCount; }
    /** @return количество треугольников */
    public int getTriangleCount()  { return triangleCount; }
    /** @return количество парабол */
    public int getParabolaCount()  { return parabolaCount; }
    /** @return количество трапеций */
    public int getTrapezoidCount() { return trapezoidCount; }

    /** @return минимальное значение X */
    public double getMinX() { return minX; }
    /** @return максимальное значение X */
    public double getMaxX() { return maxX; }
    /** @return минимальное значение Y */
    public double getMinY() { return minY; }
    /** @return максимальное значение Y */
    public double getMaxY() { return maxY; }

    /** @return параметр кучности */
    public double getDensity()  { return density; }
    /** @return шаг координатной сетки */
    public double getGridStep() { return gridStep; }
}
