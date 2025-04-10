This is a Java-based Weather Application that provides real-time weather information for any location. Here's what it does:

User Interface:
  Creates a simple GUI window with a text field to enter a location
  Displays a "Get Weather" button to fetch weather data
  Shows temperature (in both Celsius and Kelvin)
  Displays weather description
  Shows a weather icon
  
Functionality:
  Uses the OpenWeatherMap API to fetch real-time weather data
  Converts temperature from Kelvin to Celsius
  Displays weather conditions with appropriate icons
  Handles errors gracefully with user-friendly error messages
  
Technical Details:
  Built using Java Swing for the GUI
  Makes HTTP requests to the OpenWeatherMap API
  Parses JSON responses to extract weather information
  Uses multithreading (SwingUtilities.invokeLater) for smooth UI operation
