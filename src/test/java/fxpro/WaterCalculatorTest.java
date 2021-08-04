package fxpro;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class WaterCalculatorTest {

    private WaterCalculatorConfig waterCalculatorConfig;

    @Before
    public void before() {
        waterCalculatorConfig = new WaterCalculatorConfig();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteWithNullLandscape() {
        WaterCalculator waterCalculator = new WaterCalculator(waterCalculatorConfig);
        waterCalculator.calculateWaterAmount(null);
        fail("Should throw IllegalArgumentException if landscape is null");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecuteWithEmptyLandscape() {
        WaterCalculator waterCalculator = new WaterCalculator(waterCalculatorConfig);
        waterCalculator.calculateWaterAmount(new int[]{1, 2});
        fail("Should throw IllegalArgumentException if landscape is too small");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExecuteWithOversizedLandscape() {
        WaterCalculator waterCalculator = new WaterCalculator(waterCalculatorConfig);
        waterCalculator.calculateWaterAmount(new int[waterCalculatorConfig.getMaxLandscapePosition() + 1]);
        fail("Should throw IllegalArgumentException if landscape is too big");
    }

}