package fxpro;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WaterCalculatorTest {

    private WaterCalculatorConfig waterCalculatorConfig;
    private WaterCalculator waterCalculator;

    @Before
    public void before() {
        waterCalculatorConfig = new WaterCalculatorConfig();
        waterCalculator = new WaterCalculator(waterCalculatorConfig);
    }

    @Test
    public void testFewHills() {
        int[] landscape = new int[]{5, 1, 2, 4, 5, 4, 0, 3, 1};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(4, result);
    }

    @Test
    public void testNoWaterAfterEvaporate() {
        int[] landscape = new int[]{5, 4, 3, 4, 5, 4, 3, 3, 1};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(0, result);
    }

    @Test
    public void testOnlyBorderHillsWithDeepLake() {
        int[] landscape = new int[]{5, 0, 0, 0, 0, 0, 0, 0, 5};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(21, result);
    }

    @Test
    public void testCalcMaxWaterLevelsFewHills() {
        int[] landscape = new int[]{5, 1, 2, 4, 5, 4, 0, 3, 1};
        Map<Integer, Integer> localExtremes = waterCalculator.calcLocalExtremes(landscape);
        int[] waterLevels = waterCalculator.calcMaxWaterLevels(landscape, localExtremes);
        assertEquals(landscape.length, waterLevels.length);
        assertEquals(0, waterLevels[0]);
        assertEquals(4, waterLevels[1]);
        assertEquals(3, waterLevels[2]);
        assertEquals(1, waterLevels[3]);
        assertEquals(0, waterLevels[4]);
        assertEquals(0, waterLevels[5]);
        assertEquals(3, waterLevels[6]);
        assertEquals(0, waterLevels[7]);
        assertEquals(0, waterLevels[8]);
    }

    @Test
    public void testCalcMaxWaterLevelsOnePit() {
        int[] landscape = new int[]{2, 1, 3};
        Map<Integer, Integer> localExtremes = waterCalculator.calcLocalExtremes(landscape);
        int[] waterLevels = waterCalculator.calcMaxWaterLevels(landscape, localExtremes);
        assertEquals(landscape.length, waterLevels.length);
        assertEquals(0, waterLevels[0]);
        assertEquals(1, waterLevels[1]);
        assertEquals(0, waterLevels[2]);
    }

    @Test
    public void testCalcMaxWaterLevelsOneHill() {
        int[] landscape = new int[]{1, 3, 1};
        Map<Integer, Integer> localExtremes = waterCalculator.calcLocalExtremes(landscape);
        int[] waterLevels = waterCalculator.calcMaxWaterLevels(landscape, localExtremes);
        assertEquals(landscape.length, waterLevels.length);
        assertArrayEquals(new int[3], waterLevels);
    }

    @Test
    public void testCalcMaxWaterLevelsOnlyDecreasing() {
        int[] landscape = new int[]{3, 2, 1};
        Map<Integer, Integer> localExtremes = waterCalculator.calcLocalExtremes(landscape);
        int[] waterLevels = waterCalculator.calcMaxWaterLevels(landscape, localExtremes);
        assertEquals(landscape.length, waterLevels.length);
        assertArrayEquals(new int[3], waterLevels);
    }

    @Test
    public void testCalcMaxWaterLevelsIncreasing() {
        int[] landscape = new int[]{1, 2, 3};
        Map<Integer, Integer> localExtremes = waterCalculator.calcLocalExtremes(landscape);
        int[] waterLevels = waterCalculator.calcMaxWaterLevels(landscape, localExtremes);
        assertArrayEquals(new int[3], waterLevels);
    }

    @Test
    public void testLocalExtremaFewHillsWithPlateaus() {
        int[] landscape = new int[]{5, 1, 1, 5, 5, 4, 0, 0, 0};
        Map<Integer, Integer> result = waterCalculator.calcLocalExtremes(landscape);
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(5), result.get(0));
        assertEquals(Integer.valueOf(5), result.get(3));
    }


    @Test
    public void testLocalExtremaFewHills() {
        int[] landscape = new int[]{5, 1, 2, 4, 5, 4, 0, 3, 1};
        Map<Integer, Integer> result = waterCalculator.calcLocalExtremes(landscape);
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(5), result.get(0));
        assertEquals(Integer.valueOf(5), result.get(4));
        assertEquals(Integer.valueOf(3), result.get(7));
    }

    @Test
    public void testLocalExtremaOnePit() {
        int[] landscape = new int[]{2, 1, 3};
        Map<Integer, Integer> result = waterCalculator.calcLocalExtremes(landscape);
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(0));
        assertEquals(Integer.valueOf(3), result.get(2));
    }

    @Test
    public void testLocalExtremaOneHill() {
        int[] landscape = new int[]{1, 3, 1};
        Map<Integer, Integer> result = waterCalculator.calcLocalExtremes(landscape);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(1));
    }

    @Test
    public void testLocalExtremaOnlyDecreasing() {
        int[] landscape = new int[]{3, 2, 1};
        Map<Integer, Integer> result = waterCalculator.calcLocalExtremes(landscape);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(0));
    }

    @Test
    public void testLocalExtremaOnlyIncreasing() {
        int[] landscape = new int[]{1, 2, 3};
        Map<Integer, Integer> result = waterCalculator.calcLocalExtremes(landscape);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteWithNullLandscape() {
        waterCalculator.calculateWaterAmount(null);
        fail("Should throw IllegalArgumentException if landscape is null");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecuteWithEmptyLandscape() {
        waterCalculator.calculateWaterAmount(new int[]{1, 2});
        fail("Should throw IllegalArgumentException if landscape is too small");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecuteWithOversizedLandscape() {
        waterCalculator.calculateWaterAmount(new int[waterCalculatorConfig.getMaxLandscapePosition() + 1]);
        fail("Should throw IllegalArgumentException if landscape is too big");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecuteWithMinusHeight() {
        waterCalculator.calculateWaterAmount(new int[]{0, 0, -1});
        fail("Should throw IllegalArgumentException if height is minus");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecuteWithOversizedHeight() {
        waterCalculator.calculateWaterAmount(new int[]{0, 0, waterCalculatorConfig.getMaxLandscapeHeight() + 1});
        fail("Should throw IllegalArgumentException if height is minus");
    }

}