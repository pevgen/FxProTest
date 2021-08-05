package fxpro;

import java.util.HashMap;
import java.util.Map;

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

    //TODO
    protected int[] calcMaxWaterLevels(int[] landscape, Map<Integer, Integer> localExtremes) {
        return null;
    }

    protected Map<Integer, Integer> calcLocalExtremes(int[] landscape) {
        Map<Integer, Integer> localExtremes = new HashMap<>();
        // left border
        if (landscape[0] > landscape[1]) {
            localExtremes.put(0, landscape[0]);
        }
        // in the middle
        int currentMaxIndex = 0;
        for (int i = 0; i < landscape.length - 1; i++) {
            if (landscape[i] < landscape[i + 1]) {
                currentMaxIndex = i + 1;
            }
            if (landscape[i] > landscape[i + 1]) {
                localExtremes.put(currentMaxIndex, landscape[currentMaxIndex]);
            }
        }
        // right border
        if (landscape[landscape.length - 1] > landscape[landscape.length - 2]) {
            localExtremes.put(landscape.length - 1, landscape[landscape.length - 1]);
        }

        return localExtremes;
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
