/**
 * Хранит входные параметры генерации рисунка:
 * количество фигур каждого типа, область координат, кучность и шаг сетки.
 */
public class InputParameters {

    private final int lineCount;
    private final int circleCount;
    private final int rectangleCount;
    private final int triangleCount;
    private final int parabolaCount;
    private final int trapezoidCount;

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    private final double density;
    private final double gridStep;


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

    public int getLineCount()      { return lineCount; }
    public int getCircleCount()    { return circleCount; }
    public int getRectangleCount() { return rectangleCount; }
    public int getTriangleCount()  { return triangleCount; }
    public int getParabolaCount()  { return parabolaCount; }
    public int getTrapezoidCount() { return trapezoidCount; }

    public double getMinX() { return minX; }
    public double getMaxX() { return maxX; }
    public double getMinY() { return minY; }
    public double getMaxY() { return maxY; }

    public double getDensity()  { return density; }
    public double getGridStep() { return gridStep; }
}
