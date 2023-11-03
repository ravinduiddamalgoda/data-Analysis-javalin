package app;

import java.util.ArrayList;
import java.util.Map;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */

public class PageST2A implements Handler {

  // URL of this page relative to http://localhost:7001/
  public static final String URL = "/page2A.html";

  @Override
  public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html = html + "<head>" +
      "<title>Subtask 2.1</title>";

    // Add some CSS (external file)
    html = html + "<link rel='stylesheet' type='text/css' href='common.css'/>";
    html = html + "</head>";

    // Add the body
    html = html + "<body>";

    // Create JDBC Connection
    JDBCConnection jdbc = new JDBCConnection();

    // Retrieve the combined data
    ArrayList < Map < String, Object >> combinedData = jdbc.retrieveCombinedData();
    System.out.println("Number of data rows retrieved: " + combinedData.size());

    // Add the topnav
    // This uses a Java v15+ Text Block
    html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Sub Task 2.A</a>
                <a href='page2B.html'>Sub Task 2.B</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Sub Task 3.B</a>
            </div>
        """;

    // Add header content block
    html = html + """
        <div class='header'>
            <h1>Subtask 2.A</h1>
        </div>
    """;

    // Add Div for page Content
    html = html + "<div class='content'>";

    // Add HTML for the page content
    html = html + """
        <p>Subtask 2.A page content</p>
        """;

    // Start the table
    html += "<table>";
    html += "<thead><tr><th>LGAcode</th><th>Year</th><th>Indigenous Status</th><th>Sex</th><th>Age Range</th><th>Age Count</th><th>Condition</th><th>Condition Count</th></tr></thead>";
    html += "<tbody>";

    for (Map < String, Object > row: combinedData) {
      html += "<tr>";
      html += "<td>" + row.get("LGAcode") + "</td>";
      html += "<td>" + row.get("year") + "</td>";
      html += "<td>" + row.get("Indigenous_Status") + "</td>";
      html += "<td>" + row.get("Sex") + "</td>";
      html += "<td>" + row.get("AgeRange") + "</td>";
      html += "<td>" + row.get("ageCount") + "</td>";
      html += "<td>" + row.get("Condition") + "</td>";
      html += "<td>" + row.get("conditionCount") + "</td>";
      html += "</tr>";
    }

    html += "</tbody>";
    html += "</table>";

    // Close Content div
    html = html + "</div>";

    // Footer
    html = html + """ 
        <div class = 'footer'>
    <p> COSC2803 - Studio Project Starter Code(Sep23) </p> 
    </div>
    """;

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
  }
}