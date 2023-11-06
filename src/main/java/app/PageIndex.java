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
    // Generate the static part of the HTML content with Bootstrap and custom styles
    return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Homepage - Voice to Parliament</title>
            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
            <style>
    /* Custom styles */
    :root {
        --primary-color: #5b7ab3;
        --secondary-color: #4a6572;
        --accent-color: #f9aa33;
        --background-color: #f0f0f0;
        --text-color: #333;
        --footer-background-color: #333;
        --footer-text-color: #fff;
    }
    body {
        background-color: var(--background-color);
        color: var(--text-color);
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding-top: 56px; /* Padding to ensure content isn't hidden behind fixed navbar */
    }
    .navbar {
        background-color: var(--primary-color);
        border-bottom: 3px solid var(--accent-color);
    }
    .chart-container {
        animation: fadeIn 1s ease-in-out;
        padding: 15px;
        background-color: #fff;
        margin-bottom: 30px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        border-radius: 15px; /* Rounded corners for the chart containers */
    }
    @keyframes fadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }
    .footer {
        background-color: var(--footer-background-color);
        color: var(--footer-text-color);
        padding: 20px 0;
    }
</style>

        </head>
        <body>
            <!-- Navigation bar -->
            <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/">Voice to Parliament</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/mission.html">Our Mission</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/page2A.html">Age & Health</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/page2B.html">Education</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/page3A.html">Changing The Gap</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/page3B.html">Similar LGAs</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
    
            
            <!-- Page content -->
            <div class="container mt-5">
                <!-- Dynamic Content goes here -->
                <!-- Divs that will hold the charts -->
                <div class = "" > <h1> Insightful Data for Informed Decisions</h1></div>
                <div id="total_population_chart_div" class="chart-container"></div>
                <div id="state_chart_div" class="chart-container"></div>
                <div id="lga_chart_div" class="chart-container"></div>
                <!-- Additional divs for other charts can be added here -->
            </div>
        </body>
        </html>
        """;
}

private String generateStaticHtmlFooter() {
    // Generate the static HTML footer content
    return """
        <footer class="footer">
            <div class="container">
                <p class="text-center">© 2023 Voice to Parliament. All rights reserved.</p>
            </div>
        </footer>
        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.8.7/umd.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            </body>
            </html>
        """;
    }    
}