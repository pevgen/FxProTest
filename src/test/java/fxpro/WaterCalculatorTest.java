package fxpro;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
    public void testFewHillsWithPlateaus() {
        int[] landscape = new int[]{5, 1, 1, 5, 5, 4, 0, 0, 0};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(4, result);
    }

    @Test
    public void testOneDeepPit() {
        int[] landscape = new int[]{4, 1, 5};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(1, result);
    }

    @Test
    public void testOneSmallPit() {
        int[] landscape = new int[]{2, 1, 3};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(0, result);
    }

    @Test
    public void testOneHill() {
        int[] landscape = new int[]{1, 3, 1};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(0, result);
    }

    @Test
    public void testOnlyDecreasingHill() {
        int[] landscape = new int[]{3, 2, 1};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(0, result);
    }

    @Test
    public void testOnlyIncreasingHill() {
        int[] landscape = new int[]{1, 2, 3};
        long result = waterCalculator.calculateWaterAmount(landscape);
        assertEquals(0, result);
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