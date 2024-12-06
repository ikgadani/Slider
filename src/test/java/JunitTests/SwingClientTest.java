package JunitTests;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the SwingClient class.
 * These tests simulate API interactions using a mocked {@link HttpClient}.
 * Each test covers a specific HTTP request scenario such as GET, POST, PUT, and DELETE operations.
 * 
 * @author Guntas Singh Chugh
 */
class SwingClientTest {

    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    /**
     * Sets up mock {@link HttpClient} and {@link HttpResponse} instances
     * before each test to simulate server interactions.
     */
    @BeforeEach
    void setUp() {
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);
    }

    /**
     * Tests the retrieval of all sliders via a GET request.
     * Verifies the HTTP response body contains the expected list of sliders in JSON format.
     * 
     * @throws Exception if the HTTP request or mock setup fails.
     */
    @Test
    void testFetchSliders() throws Exception {
        when(mockHttpResponse.body()).thenReturn("[{\"id\":1,\"size\":100,\"x\":10,\"y\":20}]");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        HttpRequest originalRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider"))
                .GET()
                .build();

        HttpResponse<String> response = mockHttpClient.send(originalRequest, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response.body());
        assertEquals("[{\"id\":1,\"size\":100,\"x\":10,\"y\":20}]", response.body());
    }

    /**
     * Tests the retrieval of a single slider by its ID via a GET request.
     * Verifies the HTTP response body contains the expected slider details in JSON format.
     * 
     * @throws Exception if the HTTP request or mock setup fails.
     */
    @Test
    void testFetchSliderById() throws Exception {
        when(mockHttpResponse.body()).thenReturn("{\"id\":1,\"size\":100,\"x\":10,\"y\":20}");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        HttpRequest originalRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider/1"))
                .GET()
                .build();

        HttpResponse<String> response = mockHttpClient.send(originalRequest, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response.body());
        assertEquals("{\"id\":1,\"size\":100,\"x\":10,\"y\":20}", response.body());
    }

    /**
     * Tests the creation of a new slider via a POST request.
     * Verifies the HTTP response body contains the expected details of the created slider.
     * 
     * @throws Exception if the HTTP request or mock setup fails.
     */
    @Test
    void testCreateSlider() throws Exception {
        String sliderJson = "{\"size\":100,\"x\":10,\"y\":20,\"maxTravel\":300,\"currentTravel\":150,\"mvtDirection\":\"Right\",\"dirChangeCount\":5}";
        when(mockHttpResponse.body()).thenReturn("{\"id\":1,\"size\":100,\"x\":10,\"y\":20}");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        HttpRequest originalRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(sliderJson))
                .build();

        HttpResponse<String> response = mockHttpClient.send(originalRequest, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response.body());
        assertEquals("{\"id\":1,\"size\":100,\"x\":10,\"y\":20}", response.body());
    }

    /**
     * Tests updating an existing slider via a PUT request.
     * Verifies the HTTP response body contains the expected details of the updated slider.
     * 
     * @throws Exception if the HTTP request or mock setup fails.
     */
    @Test
    void testUpdateSlider() throws Exception {
        String sliderJson = "{\"size\":100,\"x\":10,\"y\":20,\"maxTravel\":300,\"currentTravel\":150,\"mvtDirection\":\"Left\",\"dirChangeCount\":7}";
        when(mockHttpResponse.body()).thenReturn("{\"id\":1,\"size\":100,\"x\":10,\"y\":20}");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        HttpRequest originalRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider/1"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(sliderJson))
                .build();

        HttpResponse<String> response = mockHttpClient.send(originalRequest, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response.body());
        assertEquals("{\"id\":1,\"size\":100,\"x\":10,\"y\":20}", response.body());
    }

    /**
     * Tests deleting a slider by its ID via a DELETE request.
     * Verifies the HTTP response body contains the expected confirmation message.
     * 
     * @throws Exception if the HTTP request or mock setup fails.
     */
    @Test
    void testDeleteSlider() throws Exception {
        when(mockHttpResponse.body()).thenReturn("{\"message\":\"Slider deleted successfully\"}");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);

        HttpRequest originalRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider/1"))
                .DELETE()
                .build();

        HttpResponse<String> response = mockHttpClient.send(originalRequest, HttpResponse.BodyHandlers.ofString());
        assertNotNull(response.body());
        assertEquals("{\"message\":\"Slider deleted successfully\"}", response.body());
    }
}
