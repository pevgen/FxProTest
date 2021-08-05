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
    public void testCalcMaxWaterLevels(){
        int[] result = waterCalculator.calcMaxWaterLevels(null, null);
        assertNotNull(result);
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

}