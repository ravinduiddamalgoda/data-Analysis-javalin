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
    // DBConnection DB =  new DBConnection();
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
    html += "<form id='query-form' action='page2A.html' method='post' onsubmit='return validateForm();'>\r\n";//
    html += "<label for='ageFilter'>Select Age Range:</label>\r\n" + //
            "    <select name='ageFilter' id='ageFilter'>\r\n" + //
            "        <option value='Default' selected>Default</option>\r\n" + //
            "        <option value='0_4'>0-4 years</option>\r\n" + //
            "        <option value='5_9'>5-9 years</option>\r\n" + //
            "        <option value='10_14'>10-14 years</option>\r\n" + //
            "        <option value='15_19'>15-19 years</option>\r\n" + //
            "        <option value='20_24'>20-24 years</option>\r\n" + //
            "        <option value='25_29'>25-29 years</option>\r\n" + //
            "        <option value='30_34'>30-34 years</option>\r\n" + //
            "        <option value='35_39'>35-39 years</option>\r\n" + //
            "        <option value='40_44'>40-44 years</option>\r\n" + //
            "        <option value='45_49'>45-49 years</option>\r\n" + //
            "        <option value='50_54'>50-54 years</option>\r\n" + //
            "        <option value='55_59'>55-59 years</option>\r\n" + //
            "        <option value='60_64'>60-64 years</option>\r\n" + //
            "        <option value='65+'>65+</option>\r\n" + //
            "    </select>\r\n" + //
            "    <br><br>\r\n" + //
            "";     

    html += " <!-- Health Condition Filter -->\r\n" + //
            "    <label for='health_condition'>Health Condition:</label>\r\n" + //
            "    <select name='health_condition' id='health_condition'>\r\n"; //
    html += "    <option value='Default' selected>Default</option>\r\n" + //
            "    <option value='arthritis'>Arthritis</option>\r\n" + //
            "    <option value='arthritis_non'>Arthritis (Non-Indigenous)</option>\r\n" + //
            "    <option value='asthma'>Asthma</option>\r\n" + //
            "    <option value='asthma_non'>Asthma (Non-Indigenous)</option>\r\n" + //
            "    <option value='cancer'>Cancer</option>\r\n" + //
            "    <option value='cancer_non'>Cancer (Non-Indigenous)</option>\r\n" + //
            "    <option value='dementia'>Dementia</option>\r\n" + //
            "    <option value='dementia_non'>Dementia (Non-Indigenous)</option>\r\n" + //
            "    <option value='diabetes'>Diabetes</option>\r\n" + //
            "    <option value='diabetes_non'>Diabetes (Non-Indigenous)</option>\r\n" + //
            "    <option value='heartdisease'>Heart Disease</option>\r\n" + //
            "    <option value='heartdisease_non'>Heart Disease (Non-Indigenous)</option>\r\n" + //
            "    <option value='kidneydisease'>Kidney Disease</option>\r\n" + //
            "    <option value='kidneydisease_non'>Kidney Disease (Non-Indigenous)</option>\r\n" + //
            "    <option value='lungcondition'>Lung Condition</option>\r\n" + //
            "    <option value='lungcondition_non'>Lung Condition (Non-Indigenous)</option>\r\n" + //
            "    <option value='mentalhealth'>Mental Health</option>\r\n" + //
            "    <option value='mentalhealth_non'>Mental Health (Non-Indigenous)</option>\r\n" + //
            "    <option value='stroke'>Stroke</option>\r\n" + //
            "    <option value='stroke_non'>Stroke (Non-Indigenous)</option>\r\n" + //
            "    <option value='other'>Other</option>\r\n" + //
            "    <option value='other_non'>Other (Non-Indigenous)</option>" + 
            "    </select>\r\n";        
    html +=        "    <br><br> ";

    // html += " <!-- Gender Filter -->\r\n" + //
    //         "    <label for='genderFilter'>Select Gender:</label>\r\n" + //
    //         "    <select name='genderFilter' id='genderFilter'>\r\n" + //
    //         "        <option value='male'>Male</option>\r\n" + //
    //         "        <option value='female'>Female</option>\r\n" + //
    //         "    </select>\r\n" + //
    //         "    <br><br>";
    
    html += " <!-- Sort Field -->\r\n" + //
            "    <label for='sortField'>Sort By:</label>\r\n" + //
            "    <select name='sortField' id='sortField'>\r\n" + //
            "        <option value='lga_code'>LGA Code</option>\r\n" + //
            "        <option value='age_group'>Age Group</option>\r\n" + //
            "    </select>\r\n" + //
            "    <br><br>";

    html += "<!-- Lga Name -->\r\n" + //
            "    <label for='LgaName'>Enter LGA Name:</label>\r\n" + //
            "  <input type='text' name = 'LgaName'>"+
            "    <br><br>";
    html += " <!-- Order By -->\r\n" + //
            "    <label for='order'>Order:</label>\r\n" + //
            "    <select name='order'>\r\n" + //
            "        <option value='ASC'>Ascending </option>\r\n" + //
            "        <option value='DESC'>Descending </option>\r\n" + //
            "    </select>\r\n" + //
            "    <br><br>";
    html += " <!-- Indigenous Status -->\r\n" + //
            "    <label for='indigenous_status'>Indigenous Status:</label>\r\n" + //
            "    <select name='indigenous_status' id='indigenous_status'>\r\n" + //
            "        <option value='Indigenous'>Indigenous</option>\r\n" + //
            "        <option value='NonIndigenous'>Non-Indigenous</option>\r\n" + //
            "        <option value='NotStated'>Not stated</option>\r\n" + //
            "    </select>\r\n" + //
            "    <br><br>";

    html += "<input type='submit' value='Submit Query'>" + "</form>";

    html += "<script>\r\n" + //
            "function validateForm() {\r\n" + //
            "    var ageFilter = document.getElementById('ageFilter').value;\r\n" + //
            "    var healthCondition = document.getElementById('health_condition').value;\r\n" + //
            "\r\n" + //
            "\r\n" + //
            "    if (ageFilter === '' || healthCondition === '') {\r\n" + //
            "        alert('Please fill out all required fields.');\r\n" + //
            "        return false; // Prevent the form from submitting\r\n" + //
            "    }\r\n" + //
            "    return true; // Allow the form to submit if validation passes\r\n" + //
            "}\r\n" + //
            "</script>";
    // Start the table
    
    // Close Content div
    html = html + "</div>";
    html += "<div>";
    // && context.formParam("health_condition") == "Default"
    if (context.formParam("LgaName") != null && context.formParams("indigenous_status") != null ) {
        // String age = context.formParam("ageFilter");
        // List<String> gender = context.formParams("genderFilter");
     
        String indigenous_status = context.formParam("indigenous_status");
        String order = context.formParam("order");
        String lgaName = context.formParam("LgaName");
        String sortField = context.formParam("sortField");
        
        ArrayList<ageCount> filteredData = jdbc.FilterAgeCount(sortField, indigenous_status , lgaName , order );
        
        if(filteredData.size() > 0){
            html += "<table>";
        // Create table headers
        html += "<tr><th>Year</th><th>Age Range</th><th>Sum</th>" +"</tr>";
        for (ageCount at : filteredData) {
            // Add data to the table
            html += "<tr><td>" + at.getAgeCount() + "</td><td>" + at.getCount() + "</td><td>" +"</tr>";
        }
        html += "</table>";
        }else {
            html += "<h2>No Data Found !!</h2>"; 
        }
        
    // Footer
    }
    html += "</div>";
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