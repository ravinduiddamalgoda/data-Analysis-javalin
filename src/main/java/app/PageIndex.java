package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public class PageIndex implements Handler {
    public static final String URL = "/";
    private static final String DATABASE_URL = "jdbc:sqlite:database/vtp.db";

    @Override
    public void handle(Context context) {
        try {
            Map<Integer, Integer> totalPopulationData = getTotalPopulationByYears(new int[]{2016, 2021});
            Map<String, Map<Integer, Integer>> statePopulationData = getStatePopulationByYears(new int[]{2016, 2021});
            Map<Integer, Integer> lgaCountData = getLGACountByYears(new int[]{2016, 2021});

            String dynamicContent = generateDynamicContent(totalPopulationData, statePopulationData, lgaCountData);
            String html = generateStaticHtmlContent() + dynamicContent + generateStaticHtmlFooter();
            context.html(html);
        } catch (Exception e) {
            context.status(500).html("An error occurred: " + e.getMessage());
        }
    }

    private Map<Integer, Integer> getTotalPopulationByYears(int[] years) throws SQLException {
        Map<Integer, Integer> totalPopulationData = new HashMap<>();
        String sql = "SELECT Year, SUM(Population) as TotalPopulation FROM LGAPopulation WHERE Year IN (?, ?) GROUP BY Year";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < years.length; i++) {
                pstmt.setInt(i + 1, years[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    totalPopulationData.put(rs.getInt("Year"), rs.getInt("TotalPopulation"));
                }
            }
        }
        return totalPopulationData;
    }

    private Map<String, Map<Integer, Integer>> getStatePopulationByYears(int[] years) throws SQLException {
        Map<String, Map<Integer, Integer>> statePopulationData = new HashMap<>();
        String sql = "SELECT LGA.stateabbr, LGA.Year, SUM(LGAPopulation.Population) as StatePopulation " +
                     "FROM LGAPopulation " +
                     "JOIN LGA ON LGAPopulation.LGAcode = LGA.lgacode " +
                     "WHERE LGA.Year IN (?, ?) " +
                     "GROUP BY LGA.stateabbr, LGA.Year";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < years.length; i++) {
                pstmt.setInt(i + 1, years[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String stateAbbr = rs.getString("stateabbr");
                    int year = rs.getInt("Year");
                    int population = rs.getInt("StatePopulation");
                    statePopulationData.computeIfAbsent(stateAbbr, k -> new HashMap<>()).put(year, population);
                }
            }
        }
        return statePopulationData;
    }

    private Map<Integer, Integer> getLGACountByYears(int[] years) throws SQLException {
        Map<Integer, Integer> lgaCounts = new HashMap<>();
        String sql = "SELECT Year, COUNT(DISTINCT lgacode) as TotalLGAs FROM LGA WHERE Year IN (?, ?) GROUP BY Year";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < years.length; i++) {
                pstmt.setInt(i + 1, years[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lgaCounts.put(rs.getInt("Year"), rs.getInt("TotalLGAs"));
                }
            }
        }
        return lgaCounts;
    }

    private String generateDynamicContent(Map<Integer, Integer> totalPopulationData, 
                                      Map<String, Map<Integer, Integer>> statePopulationData, 
                                      Map<Integer, Integer> lgaCountData) {
    Gson gson = new Gson();
    String totalPopulationJson = gson.toJson(totalPopulationData);
    String statePopulationJson = gson.toJson(statePopulationData);
    String lgaCountJson = gson.toJson(lgaCountData);

    // Format the JSON data into HTML elements
    String dataElements = 
        "<div id=\"totalPopulationData\" style=\"display: none;\">" + totalPopulationJson + "</div>\n" +
        "<div id=\"statePopulationData\" style=\"display: none;\">" + statePopulationJson + "</div>\n" +
        "<div id=\"lgaCountData\" style=\"display: none;\">" + lgaCountJson + "</div>\n";

    // Script element
    String scriptElement = 
        "<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
        "<script type=\"text/javascript\">\n" +
        "    google.charts.load('current', {'packages':['corechart']});\n" +
        "    google.charts.setOnLoadCallback(drawCharts);\n" +
        "\n" +
        "    function drawCharts() {\n" +
        "        var totalPopulationData = JSON.parse(document.getElementById('totalPopulationData').textContent);\n" +
        "        var statePopulationData = JSON.parse(document.getElementById('statePopulationData').textContent);\n" +
        "        var lgaCountData = JSON.parse(document.getElementById('lgaCountData').textContent);\n" +
        "\n" +
        "        var totalPopulationChartData = google.visualization.arrayToDataTable([\n" +
        "            ['Year', 'Population'],\n" +
        "            ['2016', totalPopulationData[2016]],\n" +
        "            ['2021', totalPopulationData[2021]]\n" +
        "        ]);\n" +
        "\n" +
        "        var statePopulationChartData = new google.visualization.DataTable();\n" +
        "        statePopulationChartData.addColumn('string', 'State');\n" +
        "        statePopulationChartData.addColumn('number', '2016');\n" +
        "        statePopulationChartData.addColumn('number', '2021');\n" +
        "        for (var state in statePopulationData) {\n" +
        "            statePopulationChartData.addRow([state, statePopulationData[state][2016], statePopulationData[state][2021]]);\n" +
        "        }\n" +
        "\n" +
        "        var lgaCountChartData = google.visualization.arrayToDataTable([\n" +
        "            ['Year', 'LGA Count'],\n" +
        "            ['2016', lgaCountData[2016]],\n" +
        "            ['2021', lgaCountData[2021]]\n" +
        "        ]);\n" +
        "\n" +
        "        var options = {\n" +
        "            title: 'Population and LGA Count',\n" +
        "            chartArea: { width: '50%' },\n" +
        "            hAxis: { title: 'Year', minValue: 0 },\n" +
        "            vAxis: { title: 'Count' }\n" +
        "        };\n" +
        "\n" +
        "        var totalPopulationChart = new google.visualization.BarChart(document.getElementById('total_population_chart_div'));\n" +
        "        totalPopulationChart.draw(totalPopulationChartData, options);\n" +
        "\n" +
        "        var stateChart = new google.visualization.BarChart(document.getElementById('state_chart_div'));\n" +
        "        stateChart.draw(statePopulationChartData, options);\n" +
        "\n" +
        "        var lgaChart = new google.visualization.BarChart(document.getElementById('lga_chart_div'));\n" +
        "        lgaChart.draw(lgaCountChartData, options);\n" +
        "    }\n" +
        "</script>\n";

    // Combine the data elements and script
    return dataElements + "\n" + scriptElement;
}
    private String generateStaticHtmlContent() {
            // Generate the static part of the HTML content
            return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Homepage - Voice to Parliament</title>
                    <!-- Include Tailwind CSS from CDN -->
                    <script src="https://cdn.tailwindcss.com"></script>
                    <!-- Link to the common CSS file -->
                    <link rel="stylesheet" href="/css/common.css">
                </head>
                <body class="bg-gray-100">
                    <!-- Navigation bar -->
                    <div class="bg-blue-600 p-4 text-white text-center">
                        <h1 class="text-xl font-semibold">Voice to Parliament</h1>
                    </div>
                    <div class="flex justify-center space-x-4 bg-blue-500 p-3">
                        <a href="/" class="text-white hover:bg-blue-700 px-3 py-2 rounded">Homepage</a>
                        <!-- Add more navigation links here -->
                    </div>
                    <!-- Page content -->
                    <main class="container mx-auto px-6 py-8">
                        <!-- Dynamic Content goes here -->
                        <!-- Divs that will hold the charts -->
                        <div id="total_population_chart_div"></div>
                        <div id="state_chart_div"></div>
                        <div id="lga_chart_div"></div>
                        <!-- Additional divs for other charts can be added here -->
                    </main>
                    <!-- Load Google Charts -->
                    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                    <script type="text/javascript">
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawCharts);
                        // Your JavaScript functions for chart drawing will go here...
                    </script>
                </body>
                </html>
                """;
        }

    private String generateStaticHtmlFooter() {
        // Generate the static HTML footer content
        return """
            <footer class='bg-white shadow mt-8'>
                <div class='container mx-auto px-6 py-4'>
                    <p class='text-gray-700 text-sm'>© 2023 Voice to Parliament. All rights reserved.</p>
                </div>
            </footer>
            <script>
            // Additional JavaScript can go here
            </script>
            </body>
            </html>
            """;
    }    
}