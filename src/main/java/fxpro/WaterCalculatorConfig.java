package fxpro;

public class WaterCalculatorConfig {

    private static final int DEFAULT_LANDSCAPE_POSITION_MIN = 3; // -_-
    private static final int DEFAULT_LANDSCAPE_POSITION_MAX = 32000;

    private final int maxLandscapePosition;
    private final int minLandscapePosition;

    public WaterCalculatorConfig() {
        this(DEFAULT_LANDSCAPE_POSITION_MIN, DEFAULT_LANDSCAPE_POSITION_MAX);
    }

    public WaterCalculatorConfig(int minLandscapePosition, int maxLandscapePosition) {
        this.minLandscapePosition = minLandscapePosition;
        this.maxLandscapePosition = maxLandscapePosition;
    }

    public int getMaxLandscapePosition() {
        return maxLandscapePosition;
    }

    public int getMinLandscapePosition() {
        return minLandscapePosition;
    }

}
