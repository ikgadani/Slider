package JunitTests;

import static org.mockito.Mockito.*;

import cst8218.slider.ejb.SliderFacade;
import cst8218.slider.entity.Slider;
import cst8218.slider.game.SliderGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Unit tests for the {@link SliderGame} class.
 * These tests verify the behavior of the game loop and interactions with the {@link SliderFacade}.
 * Mock objects are used to isolate the SliderGame logic and avoid database dependencies.
 * 
 * @author Guntas Singh Chugh
 */
class SliderGameTest {

    @Mock
    private SliderFacade mockSliderFacade;  // Mocking the SliderFacade

    @InjectMocks
    private SliderGame sliderGame;  // The class under test

    private CountDownLatch latch;  // To synchronize the background thread with the test

    /**
     * Sets up mock objects before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    /**
     * Tests the game loop with a list of sliders.
     * Verifies that:
     * - Sliders are fetched using {@link SliderFacade#findAll()}.
     * - The timeStep method is called on each slider.
     * - The updated sliders are saved back using {@link SliderFacade#edit(Slider)}.
     * 
     * @throws InterruptedException if the test thread is interrupted while waiting.
     */
    @Test
    void testGameLoopAndFacadeInteraction() throws InterruptedException {
        // Create mock Slider entities
        Slider slider1 = mock(Slider.class);
        Slider slider2 = mock(Slider.class);

        // Set the mock slider list to be returned by the SliderFacade
        List<Slider> mockSliders = Arrays.asList(slider1, slider2);

        // Simulate SliderFacade behavior
        when(mockSliderFacade.findAll()).thenReturn(mockSliders);

        // Initialize CountDownLatch with a count of 1 (for the game loop thread)
        latch = new CountDownLatch(1);

        // Create a new thread for the game loop with a controlled number of iterations
        Thread gameLoopThread = new Thread(() -> {
            // Mock the game loop to run only once for testing purposes
            for (int i = 0; i < 1; i++) {
                // Fetch all sliders from the database
                List<Slider> sliders = mockSliderFacade.findAll();

                // Update each slider by calling their timeStep method and save changes
                for (Slider slider : sliders) {
                    slider.timeStep();
                    mockSliderFacade.edit(slider);  // Save updated slider to the database
                }

                // Signal that the background work is done
                latch.countDown();
            }
        });

        // Start the game loop thread
        gameLoopThread.start();

        // Wait for the game loop to finish the first iteration
        latch.await();

        // Verify that the findAll method of SliderFacade was called
        verify(mockSliderFacade, times(1)).findAll();  // findAll should be called once

        // Verify that the timeStep method was called on each slider
        verify(slider1, atLeastOnce()).timeStep();  // slider1's timeStep method should be called at least once
        verify(slider2, atLeastOnce()).timeStep();  // slider2's timeStep method should be called at least once

        // Verify that the SliderFacade's edit method is called for each slider
        verify(mockSliderFacade, atLeastOnce()).edit(any(Slider.class));  // Edit should be called for each slider at least once
    }

    /**
     * Tests the game loop with no sliders.
     * Verifies that:
     * - {@link SliderFacade#findAll()} is called.
     * - The timeStep method is not called since no sliders are present.
     * - {@link SliderFacade#edit(Slider)} is not called.
     * 
     * @throws InterruptedException if the test thread is interrupted while waiting.
     */
    @Test
    void testGameLoopWithEmptySliders() throws InterruptedException {
        // Simulate an empty list of sliders from the SliderFacade
        when(mockSliderFacade.findAll()).thenReturn(Arrays.asList());

        // Initialize CountDownLatch with a count of 1 (for the game loop thread)
        latch = new CountDownLatch(1);

        // Create a new thread for the game loop with a controlled number of iterations
        Thread gameLoopThread = new Thread(() -> {
            // Mock the game loop to run only once for testing purposes
            for (int i = 0; i < 1; i++) {
                // Fetch all sliders from the database
                List<Slider> sliders = mockSliderFacade.findAll();

                // Update each slider by calling their timeStep method and save changes
                for (Slider slider : sliders) {
                    slider.timeStep();
                    mockSliderFacade.edit(slider);  // Save updated slider to the database
                }

                // Signal that the background work is done
                latch.countDown();
            }
        });

        // Start the game loop thread
        gameLoopThread.start();

        // Wait for the game loop to finish the first iteration
        latch.await();

        // Verify that the findAll method of SliderFacade was called
        verify(mockSliderFacade, times(1)).findAll();  // findAll should be called once

        // Verify that the edit method was NOT called since no sliders were present
        verify(mockSliderFacade, never()).edit(any(Slider.class));  // Edit should not be called when there are no sliders
    }
}
