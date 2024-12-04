import cst8218.slider.entity.Slider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SliderTest {

    @Test
    void testTimeStep_ExpectedPass() {
        // Test timeStep functionality
        Slider slider = new Slider();
        slider.setMaxTravel(10);
        slider.setCurrentTravel(0);
        slider.setMvtDirection(1);

        // Simulate movement
        slider.timeStep();
        assertEquals(5, slider.getCurrentTravel()); // TRAVEL_SPEED = 5
    }

    @Test
    void testUpdateWithNonNullValues_ExpectedPass() {
        // Test updateWithNonNullValues method
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

    @Test
    void testEqualsAndHashCode_ExpectedPass() {
        // Test equals and hashCode methods
        Slider slider1 = new Slider();
        slider1.setId(1L);

        Slider slider2 = new Slider();
        slider2.setId(1L);

        assertEquals(slider1, slider2); // Same ID, should be equal
        assertEquals(slider1.hashCode(), slider2.hashCode()); // Hash codes should match
    }

    @Test
    void testTimeStep_ExpectedFail() {
        // Test timeStep functionality with expected fail
        Slider slider = new Slider();
        slider.setMaxTravel(10);
        slider.setCurrentTravel(0);
        slider.setMvtDirection(1);

        // Simulate movement
        slider.timeStep();
        assertNotEquals(10, slider.getCurrentTravel()); // Incorrect expectation (should be 5)
    }

    @Test
    void testEqualsAndHashCode_ExpectedFail() {
        // Test equals and hashCode with expected fail
        Slider slider1 = new Slider();
        slider1.setId(1L);

        Slider slider2 = new Slider();
        slider2.setId(2L);

        assertNotEquals(slider1, slider2); // Incorrect expectation (IDs are different)
    }
}
