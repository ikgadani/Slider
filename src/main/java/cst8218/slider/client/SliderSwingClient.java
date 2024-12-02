package cst8218.slider.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SliderSwingClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Slider Management Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            frame.add(panel);

            JTextArea textArea = new JTextArea(15, 50);
            textArea.setEditable(false);

            // Input fields and buttons
            JTextField idField = new JTextField(10);
            JTextField sizeField = new JTextField(10);
            JTextField xField = new JTextField(10);
            JTextField yField = new JTextField(10);
            JTextField travelField = new JTextField(10);
            JTextField currentTravelField = new JTextField(10);
            JTextField mvtDirectionField = new JTextField(10);
            JTextField dirChangeCountField = new JTextField(10);
            JButton fetchAllButton = new JButton("Fetch All Sliders");
            JButton fetchByIdButton = new JButton("Fetch Slider by ID");
            JButton createButton = new JButton("Create Slider");
            JButton updateButton = new JButton("Update Slider");
            JButton deleteButton = new JButton("Delete Slider");

            // Layout for input fields
            JPanel inputPanel = new JPanel(new GridLayout(9, 2));
            inputPanel.add(new JLabel("Slider ID:"));
            inputPanel.add(idField);
            inputPanel.add(new JLabel("Size:"));
            inputPanel.add(sizeField);
            inputPanel.add(new JLabel("X:"));
            inputPanel.add(xField);
            inputPanel.add(new JLabel("Y:"));
            inputPanel.add(yField);
            inputPanel.add(new JLabel("Max Travel:"));
            inputPanel.add(travelField);
            inputPanel.add(new JLabel("Current Travel:"));
            inputPanel.add(currentTravelField);
            inputPanel.add(new JLabel("Mvt Direction:"));
            inputPanel.add(mvtDirectionField);
            inputPanel.add(new JLabel("Dir Change Count:"));
            inputPanel.add(dirChangeCountField);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(fetchByIdButton);
            buttonPanel.add(fetchAllButton);
            buttonPanel.add(createButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);

            panel.add(inputPanel);
            panel.add(buttonPanel);
            panel.add(new JScrollPane(textArea));

            // Fetch All Sliders
            fetchAllButton.addActionListener(e -> {
                try {
                    String sliders = fetchSliders();
                    textArea.setText(formatJson(sliders));
                } catch (Exception ex) {
                    textArea.setText("Error: Unable to fetch sliders.\n" + ex.getMessage());
                }
            });

            // Fetch Slider by ID
            fetchByIdButton.addActionListener(e -> {
                String id = idField.getText().trim();
                if (!id.isEmpty()) {
                    try {
                        String slider = fetchSliderById(id);
                        textArea.setText(formatJson(slider));
                    } catch (Exception ex) {
                        textArea.setText("Error: Unable to fetch slider.\n" + ex.getMessage());
                    }
                } else {
                    textArea.setText("Please enter a valid Slider ID.");
                }
            });

            // Create Slider
            createButton.addActionListener(e -> {
                String size = sizeField.getText().trim();
                String x = xField.getText().trim();
                String y = yField.getText().trim();
                String travel = travelField.getText().trim();
                String currentTravel = currentTravelField.getText().trim();
                String mvtDirection = mvtDirectionField.getText().trim();
                String dirChangeCount = dirChangeCountField.getText().trim();

                if (!size.isEmpty() && !x.isEmpty() && !y.isEmpty() && !travel.isEmpty() &&
                    !currentTravel.isEmpty() && !mvtDirection.isEmpty() && !dirChangeCount.isEmpty()) {
                    try {
                        String response = createSlider(size, x, y, travel, currentTravel, mvtDirection, dirChangeCount);
                        textArea.setText("Created Slider: " + response);
                    } catch (Exception ex) {
                        textArea.setText("Error: Unable to create slider.\n" + ex.getMessage());
                    }
                } else {
                    textArea.setText("Please enter all values.");
                }
            });

            // Update Slider
            updateButton.addActionListener(e -> {
                String id = idField.getText().trim();
                String size = sizeField.getText().trim();
                String x = xField.getText().trim();
                String y = yField.getText().trim();
                String travel = travelField.getText().trim();
                String currentTravel = currentTravelField.getText().trim();
                String mvtDirection = mvtDirectionField.getText().trim();
                String dirChangeCount = dirChangeCountField.getText().trim();

                if (!id.isEmpty() && !size.isEmpty() && !x.isEmpty() && !y.isEmpty() &&
                    !travel.isEmpty() && !currentTravel.isEmpty() && !mvtDirection.isEmpty() && !dirChangeCount.isEmpty()) {
                    try {
                        String response = updateSlider(id, size, x, y, travel, currentTravel, mvtDirection, dirChangeCount);
                        textArea.setText("Updated Slider: " + response);
                    } catch (Exception ex) {
                        textArea.setText("Error: Unable to update slider.\n" + ex.getMessage());
                    }
                } else {
                    textArea.setText("Please enter valid values.");
                }
            });

            // Delete Slider (Only ID required)
            deleteButton.addActionListener(e -> {
                String id = idField.getText().trim();
                if (!id.isEmpty()) {
                    try {
                        String response = deleteSlider(id);
                        textArea.setText("Deleted Slider: " + response);
                    } catch (Exception ex) {
                        textArea.setText("Error: Unable to delete slider.\n" + ex.getMessage());
                    }
                } else {
                    textArea.setText("Please enter a valid Slider ID.");
                }
            });

            frame.setVisible(true);
        });
    }

    // Fetch all sliders
    private static String fetchSliders() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Fetch a slider by ID
    private static String fetchSliderById(String id) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Create a new slider
    private static String createSlider(String size, String x, String y, String travel, String currentTravel, String mvtDirection, String dirChangeCount) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format(
                "{\"size\": %s, \"x\": %s, \"y\": %s, \"maxTravel\": %s, \"currentTravel\": %s, \"mvtDirection\": \"%s\", \"dirChangeCount\": %s}",
                size, x, y, travel, currentTravel, mvtDirection, dirChangeCount
        );
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Update an existing slider
    private static String updateSlider(String id, String size, String x, String y, String travel, String currentTravel, String mvtDirection, String dirChangeCount) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format(
                "{\"size\": %s, \"x\": %s, \"y\": %s, \"maxTravel\": %s, \"currentTravel\": %s, \"mvtDirection\": \"%s\", \"dirChangeCount\": %s}",
                size, x, y, travel, currentTravel, mvtDirection, dirChangeCount
        );
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Delete a slider by ID
    private static String deleteSlider(String id) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/Slider/resources/cst8218.slider.entity.slider/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Format JSON response for display
    private static String formatJson(String json) {
        return json.replaceAll("},", "},\n").replaceAll("\\{", "{\n").replaceAll("\\}", "\n}");
    }
}