package fxpro;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

public class WaterCalculator {

    private final WaterCalculatorConfig waterCalculatorConfig;

    public WaterCalculator(WaterCalculatorConfig waterCalculatorConfig) {
        this.waterCalculatorConfig = waterCalculatorConfig;
    }

    public long calculateWaterAmount(int[] landscape) {
        checkLandscapeSize(landscape);
        var localExtremes = calcLocalExtremes(landscape);
        var water = calcMaxWaterLevels(landscape, localExtremes);
        return evaporateAndCalcAmount(water);
    }

    protected final int[] calcMaxWaterLevels(int[] landscape, Map<Integer, Integer> localExtremes) {
        int[] water = new int[landscape.length];
        Integer[] extremeIndexList = localExtremes.keySet().toArray(new Integer[0]);

        for (int i = 0; i < extremeIndexList.length - 1; i++) {
            var currentExtreme = localExtremes.get(extremeIndexList[i]);
            var nextExtreme = localExtremes.get(extremeIndexList[i + 1]);
            int waterLevel = Math.min(currentExtreme, nextExtreme);
            fillWater(landscape, water, waterLevel, extremeIndexList[i], extremeIndexList[i + 1]);
        }
        return water;
    }

    protected final Map<Integer, Integer> calcLocalExtremes(int[] landscape) {
        Map<Integer, Integer> localExtremes = new TreeMap<>();

        processLeftBorder(landscape, localExtremes);
        processBetweenBorders(landscape, localExtremes);
        processRightBorder(landscape, localExtremes);

        return localExtremes;
    }

    private void fillWater(int[] landscape, int[] water, int waterLevel, Integer indexFrom, Integer indexTo) {
        for (int i = indexFrom; i < indexTo; i++) {
            water[i] = Math.max(waterLevel - landscape[i], 0);
        }
    }

    private long evaporateAndCalcAmount(int[] water) {
        return Arrays.stream(water)
                .asLongStream()
                .map(level -> Math.max(level - waterCalculatorConfig.getEvaporateLevel(), 0))
                .sum();
    }

    private void processBetweenBorders(int[] landscape, Map<Integer, Integer> localExtremes) {
        int currentMaxIndex = 0;
        for (int i = 0; i < landscape.length - 1; i++) {
            if (landscape[i] < landscape[i + 1]) {
                currentMaxIndex = i + 1;
            }
            if (landscape[i] > landscape[i + 1]) {
                localExtremes.put(currentMaxIndex, landscape[currentMaxIndex]);
            }
        }
    }

    private void processRightBorder(int[] landscape, Map<Integer, Integer> localExtremes) {
        if (landscape[landscape.length - 1] > landscape[landscape.length - 2]) {
            localExtremes.put(landscape.length - 1, landscape[landscape.length - 1]);
        }
    }

    private void processLeftBorder(int[] landscape, Map<Integer, Integer> localExtremes) {
        if (landscape[0] > landscape[1]) {
            localExtremes.put(0, landscape[0]);
        }
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

        int minHeight = IntStream.of(landscape).min().orElse(Integer.MIN_VALUE);
        int maxHeight = IntStream.of(landscape).max().orElse(Integer.MAX_VALUE);

        if (minHeight < 0
                || maxHeight > waterCalculatorConfig.getMaxLandscapeHeight()) {
            throw new IndexOutOfBoundsException(
                    "Height of hills are not in the range [0, "
                            + waterCalculatorConfig.getMaxLandscapeHeight()
                            + " ]");
        }

    }

}
