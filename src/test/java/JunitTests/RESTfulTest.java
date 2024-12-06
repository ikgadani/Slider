package JunitTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import cst8218.slider.entity.Slider;
import cst8218.slider.service.SliderFacadeREST;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;

/**
 * Unit tests for the {@link SliderFacadeREST} class.
 * These tests verify the behavior of RESTful endpoints for managing {@link Slider} entities.
 * Mockito is used to mock dependencies, ensuring tests are isolated.
 * 
 * @author Guntas Singh Chugh
 */
public class RESTfulTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private UriInfo mockUriInfo;

    private SliderFacadeREST sliderFacadeREST;

    /**
     * Initializes mock objects and the {@link SliderFacadeREST} instance before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sliderFacadeREST = new SliderFacadeREST();
        sliderFacadeREST.setEntityManager(mockEntityManager);
    }

    /**
     * Tests the creation of a new {@link Slider} entity via POST.
     * Verifies that the entity is persisted and the correct response is returned.
     */
    @Test
    void testCreatePost() {
        Slider slider = new Slider();
        slider.setId(1L);

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create("http://localhost/api/sliders"));

        Response response = sliderFacadeREST.createPost(slider, mockUriInfo);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(slider, response.getEntity());
        verify(mockEntityManager, times(1)).persist(slider);
    }

    /**
     * Tests editing an existing {@link Slider} entity.
     * Verifies that the entity is updated and the correct response is returned.
     */
    @Test
    void testEdit() {
        Slider slider = new Slider();
        slider.setId(1L);

        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(slider);

        Response response = sliderFacadeREST.edit(1L, slider);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(slider, response.getEntity());
        verify(mockEntityManager, times(1)).merge(slider);
    }

    /**
     * Tests editing a {@link Slider} entity that does not exist.
     * Verifies that a NOT_FOUND response is returned.
     */
    @Test
    void testEdit_NotFound() {
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(null);

        Slider slider = new Slider();
        Response response = sliderFacadeREST.edit(1L, slider);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(mockEntityManager, never()).merge(any(Slider.class));
    }

    /**
     * Tests removing a {@link Slider} entity that does not exist.
     * Verifies that a NOT_FOUND response is returned and no removal occurs.
     */
    @Test
    void testRemove_NotFound() {
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(null);

        Response response = sliderFacadeREST.remove(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(mockEntityManager, never()).remove(any(Slider.class));
    }

    /**
     * Tests finding an existing {@link Slider} entity by ID.
     * Verifies that the correct entity and response are returned.
     */
    @Test
    void testFind() {
        Slider slider = new Slider();
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(slider);

        Response response = sliderFacadeREST.find(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(slider, response.getEntity());
    }

    /**
     * Tests finding a {@link Slider} entity that does not exist.
     * Verifies that a NOT_FOUND response is returned.
     */
    @Test
    void testFind_NotFound() {
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(null);

        Response response = sliderFacadeREST.find(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    /**
     * Tests replacing an existing {@link Slider} entity.
     * Verifies that the entity's attributes are updated and persisted.
     */
    @Test
    void testReplaceSlider() {
        Slider existingSlider = new Slider();
        existingSlider.setId(1L);

        Slider newSlider = new Slider();
        newSlider.setSize(100);

        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(existingSlider);

        Response response = sliderFacadeREST.replaceSlider(1L, newSlider);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(mockEntityManager, times(1)).merge(existingSlider);
        assertEquals(100, existingSlider.getSize());
    }

    /**
     * Tests handling a PUT request to the root endpoint.
     * Verifies that a METHOD_NOT_ALLOWED response is returned.
     */
    @Test
    void testHandlePutOnRoot() {
        Response response = sliderFacadeREST.handlePutOnRoot();

        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }
}
