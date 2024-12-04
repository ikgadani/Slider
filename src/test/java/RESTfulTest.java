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

public class RESTfulTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private UriInfo mockUriInfo;

    private SliderFacadeREST sliderFacadeREST;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sliderFacadeREST = new SliderFacadeREST();
        sliderFacadeREST.setEntityManager(mockEntityManager);
    }

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

    @Test
    void testEdit_NotFound() {
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(null);

        Slider slider = new Slider();
        Response response = sliderFacadeREST.edit(1L, slider);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(mockEntityManager, never()).merge(any(Slider.class));
    }

    @Test
    void testRemove_NotFound() {
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(null);

        Response response = sliderFacadeREST.remove(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(mockEntityManager, never()).remove(any(Slider.class));
    }

    @Test
    void testFind() {
        Slider slider = new Slider();
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(slider);

        Response response = sliderFacadeREST.find(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(slider, response.getEntity());
    }

    @Test
    void testFind_NotFound() {
        when(mockEntityManager.find(Slider.class, 1L)).thenReturn(null);

        Response response = sliderFacadeREST.find(1L);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
        
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

    @Test
    void testHandlePutOnRoot() {
        Response response = sliderFacadeREST.handlePutOnRoot();

        assertEquals(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatus());
    }
}
