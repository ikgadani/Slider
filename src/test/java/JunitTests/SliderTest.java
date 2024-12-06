package JunitTests;

import cst8218.slider.entity.Slider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Slider} class.
 * These tests verify key functionalities, including time step movement,
 * updating with non-null values, and equality and hash code behavior.
 * 
 * @author Guntas Singh Chugh
 */
class SliderTest {

    /**
     * Tests the {@link Slider#timeStep()} method to ensure that the slider's 
     * current travel value is updated correctly based on movement direction and speed.
     */
    @Test
    void testTimeStep_ExpectedPass() {
        Slider slider = new Slider();
        slider.setMaxTravel(10);
        slider.setCurrentTravel(0);
        slider.setMvtDirection(1);

        slider.timeStep();

        assertEquals(5, slider.getCurrentTravel()); // TRAVEL_SPEED = 5
    }

    /**
     * Tests the {@link Slider#updateWithNonNullValues(Slider)} method to ensure 
     * that only non-null values from the new slider are used to update the original slider.
     */
    @Test
    void testUpdateWithNonNullValues_ExpectedPass() {
        Slider originalSlider = new Slider();
        originalSlider.setSize(50);
        originalSlider.setX(100);

        Slider newSlider = new Slider();
        newSlider.setSize(80); // Should update
        newSlider.setX(null); // Should not update

        originalSlider.updateWithNonNullValues(newSlider);

        assertEquals(80, originalSlider.getSize()); // Updated
        assertEquals(100, originalSlider.getX()); // Unchanged
    }

    /**
     * Tests the {@link Slider#equals(Object)} and {@link Slider#hashCode()} methods to verify 
     * that sliders with the same ID are considered equal and have matching hash codes.
     */
    @Test
    void testEqualsAndHashCode_ExpectedPass() {
        Slider slider1 = new Slider();
        slider1.setId(1L);

        Slider slider2 = new Slider();
        slider2.setId(1L);

        assertEquals(slider1, slider2); // Same ID, should be equal
        assertEquals(slider1.hashCode(), slider2.hashCode()); // Hash codes should match
    }

    /**
     * Tests the {@link Slider#timeStep()} method with an incorrect expectation,
     * to demonstrate a test failure.
     */
    @Test
    void testTimeStep_ExpectedFail() {
        Slider slider = new Slider();
        slider.setMaxTravel(10);
        slider.setCurrentTravel(0);
        slider.setMvtDirection(1);

        slider.timeStep();

        assertNotEquals(10, slider.getCurrentTravel()); // Incorrect expectation (should be 5)
    }

    /**
     * Tests the {@link Slider#equals(Object)} and {@link Slider#hashCode()} methods with 
     * different IDs, demonstrating a failure when they are expected to be equal.
     */
    @Test
    void testEqualsAndHashCode_ExpectedFail() {
        Slider slider1 = new Slider();
        slider1.setId(1L);

        Slider slider2 = new Slider();
        slider2.setId(2L);

        assertNotEquals(slider1, slider2); // Correctly fail as IDs are different
    }
}
