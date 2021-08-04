package fxpro;

import static java.util.Objects.isNull;

public class WaterCalculator {

    private final WaterCalculatorConfig waterCalculatorConfig;

    public WaterCalculator(WaterCalculatorConfig waterCalculatorConfig) {
        this.waterCalculatorConfig = waterCalculatorConfig;
    }

    public long calculateWaterAmount(int[] landscape) {
        checkLandscapeSize(landscape);
        return 0;
    }

    private void checkLandscapeSize(int[] landscape) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (isNull(landscape)) {
            throw new IllegalArgumentException("Parameter 'landscape' can't be null");
        }

        if (landscape.length < waterCalculatorConfig.getMinLandscapePosition()
                || landscape.length > waterCalculatorConfig.getMaxLandscapePosition()) {
            throw new IndexOutOfBoundsException(
                    "Parameter 'landscape' = [" + landscape.length
                            + "] not in range [" + waterCalculatorConfig.getMinLandscapePosition()
                            + ", " + waterCalculatorConfig.getMinLandscapePosition()
                            + " ]");
        }

    }

    public static void main(String[] str) {
        System.out.println("asdf");
    }

}
