import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SwingClientTest {

    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() {
        // Set up mock HttpClient and HttpResponse to simulate API interactions
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);
    }

    @Test
    void testFetchSliders() throws Exception {
        // Test fetching all sliders
        // Simulates a GET request to retrieve a list of sliders and verifies the response
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

    @Test
    void testFetchSliderById() throws Exception {
        // Test fetching a single slider by its ID
        // Simulates a GET request to retrieve a specific slider and verifies the response
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

    @Test
    void testCreateSlider() throws Exception {
        // Test creating a new slider
        // Simulates a POST request with slider details and verifies the response
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

    @Test
    void testUpdateSlider() throws Exception {
        // Test updating an existing slider
        // Simulates a PUT request to modify a slider's details and verifies the response
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

    @Test
    void testDeleteSlider() throws Exception {
        // Test deleting a slider by its ID
        // Simulates a DELETE request to remove a slider and verifies the response
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
