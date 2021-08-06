package fxpro;

public class WaterCalculatorConfig {

    private static final int DEFAULT_LANDSCAPE_POSITION_MIN = 3;   // minimum one pit -_-
    private static final int DEFAULT_LANDSCAPE_POSITION_MAX = 32000;
    private static final int DEFAULT_LANDSCAPE_HEIGHT_MAX = 32000;
    private static final int DEFAULT_EVAPORATE_LEVEL = 2;

    private final int maxLandscapePosition;
    private final int minLandscapePosition;
    private final int maxLandscapeHeight;
    private final int evaporateLevel;

    public WaterCalculatorConfig() {
        this(DEFAULT_LANDSCAPE_POSITION_MIN,
                DEFAULT_LANDSCAPE_POSITION_MAX,
                DEFAULT_LANDSCAPE_HEIGHT_MAX,
                DEFAULT_EVAPORATE_LEVEL);
    }

    public WaterCalculatorConfig(
            final int minLandscapePosition,
            final int maxLandscapePosition,
            final int maxLandscapeHeight,
            final int evaporateLevel) {
        this.minLandscapePosition = minLandscapePosition;
        this.maxLandscapePosition = maxLandscapePosition;
        this.maxLandscapeHeight = maxLandscapeHeight;
        this.evaporateLevel = evaporateLevel;
    }

    public int getMaxLandscapePosition() {
        return maxLandscapePosition;
    }

    public int getMinLandscapePosition() {
        return minLandscapePosition;
    }

    public int getMaxLandscapeHeight() {
        return maxLandscapeHeight;
    }

    public int getEvaporateLevel() {
        return evaporateLevel;
    }
}
