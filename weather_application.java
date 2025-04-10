import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {
    private JFrame frame;
    private JTextField locationField;
    private JLabel temperatureLabel, descriptionLabel, iconLabel;
    private static final String API_KEY = "0b59ee08497d20ce42a6cfeec2c12255";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    public WeatherApp() {
        frame = new JFrame("Weather App");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel locationLabel = new JLabel("Enter Location:");
        locationLabel.setBounds(10, 20, 80, 25);
        panel.add(locationLabel);

        locationField = new JTextField(20);
        locationField.setBounds(100, 20, 160, 25);
        panel.add(locationField);

        JButton getWeatherButton = new JButton("Get Weather");
        getWeatherButton.setBounds(280, 20, 120, 25);
        panel.add(getWeatherButton);

        temperatureLabel = new JLabel("Temperature: ");
        temperatureLabel.setBounds(10, 50, 300, 25);
        panel.add(temperatureLabel);

        descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setBounds(10, 80, 300, 25);
        panel.add(descriptionLabel);

        iconLabel = new JLabel();
        iconLabel.setBounds(350, 50, 50, 50);
        panel.add(iconLabel);

        getWeatherButton.addActionListener(e -> fetchWeatherData(locationField.getText()));
        frame.setVisible(true);
    }

    private void fetchWeatherData(String location) {
        try {
            URL url = new URL(String.format("%s?q=%s&appid=%s", API_URL, location, API_KEY));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            reader.close();
            connection.disconnect();

            JSONObject json = new JSONObject(response.toString());
            JSONObject main = json.getJSONObject("main");
            String temperature = String.valueOf(main.getDouble("temp"));
            
            JSONArray weatherArray = json.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            String description = weather.getString("description");
            String iconCode = weather.getString("icon");
            
            updateWeatherGUI(temperature, description, iconCode);
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    private void updateWeatherGUI(String temperature, String description, String iconCode) {
        float celsius = Float.parseFloat(temperature) - 273.13f;
        temperatureLabel.setText(String.format("Temperature: %s°C and %s°K", celsius, temperature));
        descriptionLabel.setText("Description: " + description);
        
        try {
            iconLabel.setIcon(new ImageIcon(new URL("http://openweathermap.org/img/w/" + iconCode + ".png")));
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleException(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame, "Error fetching weather data.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherApp::new);
    }
}