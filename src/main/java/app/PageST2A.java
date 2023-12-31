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
    html += "<script src='https://cdn.tailwindcss.com'></script>";
    html+= "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>";
    html += "         <style>\r\n" + //
            "    /* Custom styles */\r\n" + //
            "    :root {\r\n" + //
            "        --primary-color: #5b7ab3;\r\n" + //
            "        --secondary-color: #4a6572;\r\n" + //
            "        --accent-color: #f9aa33;\r\n" + //
            "        --background-color: #f0f0f0;\r\n" + //
            "        --text-color: #333;\r\n" + //
            "        --footer-background-color: #333;\r\n" + //
            "        --footer-text-color: #fff;\r\n" + //
            "    }\r\n" + //
            "    body {\r\n" + //
            "        background-color: var(--background-color);\r\n" + //
            "        color: var(--text-color);\r\n" + //
            "        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\r\n" + //
            "        margin: 0;\r\n" + //
            "        padding-top: 56px; /* Padding to ensure content isn't hidden behind fixed navbar */\r\n" + //
            "    }\r\n" + //
            "    .navbar {\r\n" + //
            "        background-color: var(--primary-color);\r\n" + //
            "        border-bottom: 3px solid var(--accent-color);\r\n" + //
            "    }\r\n" + //
            "    .chart-container {\r\n" + //
            "        animation: fadeIn 1s ease-in-out;\r\n" + //
            "        padding: 15px;\r\n" + //
            "        background-color: #fff;\r\n" + //
            "        margin-bottom: 30px;\r\n" + //
            "        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\r\n" + //
            "        border-radius: 15px; /* Rounded corners for the chart containers */\r\n" + //
            "    }\r\n" + //
            "    @keyframes fadeIn {\r\n" + //
            "        from { opacity: 0; }\r\n" + //
            "        to { opacity: 1; }\r\n" + //
            "    }\r\n" + //
            "    .footer {\r\n" + //
            "        background-color: var(--footer-background-color);\r\n" + //
            "        color: var(--footer-text-color);\r\n" + //
            "        padding: 20px 0;\r\n" + //
            "    }\r\n" + //
            "</style>";
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
   html += "<nav class='navbar navbar-expand-lg navbar-dark fixed-top'>\r\n" + //
           "    <div class='container'>\r\n" + //
           "        <a class='navbar-brand' href='/'>Voice to Parliament</a>\r\n" + //
           "        <button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarNav'\r\n" + //
           "                aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'>\r\n" + //
           "            <span class='navbar-toggler-icon'></span>\r\n" + //
           "        </button>\r\n" + //
           "        <div class='collapse navbar-collapse' id='navbarNav'>\r\n" + //
           "            <ul class='navbar-nav ml-auto'>\r\n" + //
           "                <li class='nav-item active'>\r\n" + //
           "                    <a class='nav-link' href='/'>Home</span></a>\r\n" + //
           "                </li>\r\n" + //
           "                <li class='nav-item'>\r\n" + //
           "                    <a class='nav-link' href='/mission.html'>Our Mission</a>\r\n" + //
           "                </li>\r\n" + //
           "                <li class='nav-item'>\r\n" + //
           "                    <a class='nav-link' href='/page2A.html'>Age & Health</a>\r\n" + //
           "                </li>\r\n" + //
           "                <li class='nav-item'>\r\n" + //
           "                    <a class='nav-link' href='/page2B.html'>Education</a>\r\n" + //
           "                </li>\r\n" + //
           "                <li class='nav-item'>\r\n" + //
           "                    <a class='nav-link' href='/page3A.html'>Changing The Gap</a>\r\n" + //
           "                </li>\r\n" + //
           "                <li class='nav-item'>\r\n" + //
           "                    <a class='nav-link' href='/page3B.html'>Similar LGAs</a>\r\n" + //
           "                </li>\r\n" + //
           "            </ul>\r\n" + //
           "        </div>\r\n" + //
           "    </div>\r\n" + //
           "</nav>\r\n" + //
           "";

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

    html += "<div class='p-4 max-w-md mx-auto'>\r\n" + //
            "    <form id='query-form' action='page2A.html' method='post' onsubmit='return validateForm();'>\r\n" + //
            "        <div class='mb-4'>\r\n" + //
            "            <label for='ageFilter' class='text-blue-500'>Select Age Range:</label>\r\n" + //
            "            <select name='ageFilter' id='ageFilter' class='bg-blue-100 p-2 rounded'>\r\n" + //
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
            "            </select>\r\n" + //
            "        </div>\r\n" + //
            "\r\n" + //
            "        <!-- Health Condition Filter -->\r\n" + //
            "        <div class='mb-4'>\r\n" + //
            "            <label for='health_condition' class='text-blue-500'>Health Condition:</label>\r\n" + //
            "            <select name='health_condition' id='health_condition' class='bg-blue-100 p-2 rounded'>\r\n" + //
            "    <option value='arthritis'>Arthritis</option>\r\n" + //
            "    <option value='asthma'>Asthma</option>\r\n" + //
            "    <option value='cancer'>Cancer</option>\r\n" + //
            "    <option value='dementia'>Dementia</option>\r\n" + //
            "    <option value='diabetes'>Diabetes</option>\r\n" + //
            "    <option value='heartdisease'>Heart Disease</option>\r\n" + //
            "    <option value='kidneydisease'>Kidney Disease</option>\r\n" + //
            "    <option value='lungcondition'>Lung Condition</option>\r\n" + //
            "    <option value='mentalhealth'>Mental Health</option>\r\n" + //
            "    <option value='stroke'>Stroke</option>\r\n" + //
        "    <option value='other'>Other</option>\r\n" + //
            "            </select>\r\n" + //
            "        </div>\r\n" + //
            "\r\n" + //
            "        <!-- Sort Field -->\r\n" + //
            "        <div class='mb-4'>\r\n" + //
            "            <label for='sortField' class='text-blue-500'>Sort By:</label>\r\n" + //
            "            <select name='sortField' id='sortField' class='bg-blue-100 p-2 rounded'>\r\n" + //
            "                <option value='lga_code'>LGA Code</option>\r\n" + //
            "                <option value='age_group'>Age Group</option>\r\n" + //
            "            </select>\r\n" + //
            "        </div>\r\n" + //
            "\r\n" + //
            "        <!-- Lga Name -->\r\n" + //
            "        <div class='mb-4'>\r\n" + //
            "            <label for='LgaName' class='text-blue-500'>Enter LGA Name:</label>\r\n" + //
            "            <input type='text' name='LgaName' class='bg-blue-100 p-2 rounded'>\r\n" + //
            "        </div>\r\n" + //
            "\r\n" + //
            "        <!-- Order By -->\r\n" + //
            "        <div class='mb-4'>\r\n" + //
            "            <label for='order' class='text-blue-500'>Order:</label>\r\n" + //
            "            <select name='order' class='bg-blue-100 p-2 rounded'>\r\n" + //
            "                <option value='ASC'>Ascending</option>\r\n" + //
            "                <option value='DESC'>Descending</option>\r\n" + //
            "            </select>\r\n" + //
            "        </div>\r\n" + //
            "\r\n" + //
            "        <!-- Indigenous Status -->\r\n" + //
            "        <div class='mb-4'>\r\n" + //
            "            <label for='indigenous_status' class='text-blue-500'>Indigenous Status:</label>\r\n" + //
            "            <select name='indigenous_status' id='indigenous_status' class='bg-blue-100 p-2 rounded'>\r\n" + //
            "                <option value='Indigenous'>Indigenous</option>\r\n" + //
            "                <option value='NonIndigenous'>Non-Indigenous</option>\r\n" + //
            "                <option value='NotStated'>Not stated</option>\r\n" + //
            "            </select>\r\n" + //
            "        </div>\r\n" + //
            "\r\n" + //
            "        <div class='mt-4'>\r\n" + //
            "            <input type='submit' value='Submit Query' class='bg-blue-500 text-white p-2 rounded cursor-pointer'>\r\n" + //
            "        </div>\r\n" + //
            "    </form>\r\n" + //
            "</div>\r\n" + //
            "";    
    // html += "<form id='query-form' action='page2A.html' method='post' onsubmit='return validateForm();'>\r\n";//
    // html += "<label for='ageFilter'>Select Age Range:</label>\r\n" + //
    //         "    <select name='ageFilter' id='ageFilter'>\r\n" + //
    //         // "        <option value='Default' selected>Default</option>\r\n" + //
    //         "        <option value='0_4'>0-4 years</option>\r\n" + //
    //         "        <option value='5_9'>5-9 years</option>\r\n" + //
    //         "        <option value='10_14'>10-14 years</option>\r\n" + //
    //         "        <option value='15_19'>15-19 years</option>\r\n" + //
    //         "        <option value='20_24'>20-24 years</option>\r\n" + //
    //         "        <option value='25_29'>25-29 years</option>\r\n" + //
    //         "        <option value='30_34'>30-34 years</option>\r\n" + //
    //         "        <option value='35_39'>35-39 years</option>\r\n" + //
    //         "        <option value='40_44'>40-44 years</option>\r\n" + //
    //         "        <option value='45_49'>45-49 years</option>\r\n" + //
    //         "        <option value='50_54'>50-54 years</option>\r\n" + //
    //         "        <option value='55_59'>55-59 years</option>\r\n" + //
    //         "        <option value='60_64'>60-64 years</option>\r\n" + //
    //         "        <option value='65+'>65+</option>\r\n" + //
    //         "    </select>\r\n" + //
    //         "    <br><br>\r\n" + //
    //         "";     

    // html += " <!-- Health Condition Filter -->\r\n" + //
    //         "    <label for='health_condition'>Health Condition:</label>\r\n" + //
    //         "    <select name='health_condition' id='health_condition'>\r\n"; //
    // html += "    <option value='arthritis'>Arthritis</option>\r\n" + //
    //         "    <option value='Default' selected>Default</option>\r\n" + //
    //         "    <option value='arthritis_non'>Arthritis (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='asthma'>Asthma</option>\r\n" + //
    //         "    <option value='asthma_non'>Asthma (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='cancer'>Cancer</option>\r\n" + //
    //         "    <option value='cancer_non'>Cancer (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='dementia'>Dementia</option>\r\n" + //
    //         "    <option value='dementia_non'>Dementia (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='diabetes'>Diabetes</option>\r\n" + //
    //         "    <option value='diabetes_non'>Diabetes (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='heartdisease'>Heart Disease</option>\r\n" + //
    //         "    <option value='heartdisease_non'>Heart Disease (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='kidneydisease'>Kidney Disease</option>\r\n" + //
    //         "    <option value='kidneydisease_non'>Kidney Disease (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='lungcondition'>Lung Condition</option>\r\n" + //
    //         "    <option value='lungcondition_non'>Lung Condition (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='mentalhealth'>Mental Health</option>\r\n" + //
    //         "    <option value='mentalhealth_non'>Mental Health (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='stroke'>Stroke</option>\r\n" + //
    //         "    <option value='stroke_non'>Stroke (Non-Indigenous)</option>\r\n" + //
    //         "    <option value='other'>Other</option>\r\n" + //
    //         "    <option value='other_non'>Other (Non-Indigenous)</option>" + 
    //         "    </select>\r\n";        
    // html +=        "    <br><br> ";

    // // html += " <!-- Gender Filter -->\r\n" + //
    // //         "    <label for='genderFilter'>Select Gender:</label>\r\n" + //
    // //         "    <select name='genderFilter' id='genderFilter'>\r\n" + //
    // //         "        <option value='male'>Male</option>\r\n" + //
    // //         "        <option value='female'>Female</option>\r\n" + //
    // //         "    </select>\r\n" + //
    // //         "    <br><br>";
    
    // html += " <!-- Sort Field -->\r\n" + //
    //         "    <label for='sortField'>Sort By:</label>\r\n" + //
    //         "    <select name='sortField' id='sortField'>\r\n" + //
    //         "        <option value='lga_code'>LGA Code</option>\r\n" + //
    //         "        <option value='age_group'>Age Group</option>\r\n" + //
    //         "    </select>\r\n" + //
    //         "    <br><br>";

    // html += "<!-- Lga Name -->\r\n" + //
    //         "    <label for='LgaName'>Enter LGA Name:</label>\r\n" + //
    //         "  <input type='text' name = 'LgaName'>"+
    //         "    <br><br>";
    // html += " <!-- Order By -->\r\n" + //
    //         "    <label for='order'>Order:</label>\r\n" + //
    //         "    <select name='order'>\r\n" + //
    //         "        <option value='ASC'>Ascending </option>\r\n" + //
    //         "        <option value='DESC'>Descending </option>\r\n" + //
    //         "    </select>\r\n" + //
    //         "    <br><br>";
    // html += " <!-- Indigenous Status -->\r\n" + //
    //         "    <label for='indigenous_status'>Indigenous Status:</label>\r\n" + //
    //         "    <select name='indigenous_status' id='indigenous_status'>\r\n" + //
    //         "        <option value='Indigenous'>Indigenous</option>\r\n" + //
    //         "        <option value='NonIndigenous'>Non-Indigenous</option>\r\n" + //
    //         "        <option value='NotStated'>Not stated</option>\r\n" + //
    //         "    </select>\r\n" + //
    //         "    <br><br>";

    // html += "<input type='submit' value='Submit Query'>" + "</form>";

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
    boolean ageData = false;
    String validationData =  context.formParam("health_condition");
    if(validationData == null || validationData.equals("Default")){
        ageData = true;
    }
    if (context.formParam("LgaName") != null && context.formParams("indigenous_status") != null && ageData == true) {
        // String age = context.formParam("ageFilter");
        // List<String> gender = context.formParams("genderFilter");
        String age = context.formParam("ageFilter");
        String indigenous_status = context.formParam("indigenous_status");
        String order = context.formParam("order");
        String lgaName = context.formParam("LgaName");
        String sortField = context.formParam("sortField");
        
        ArrayList<ageCount> filteredData = jdbc.FilterAgeCount(sortField, indigenous_status , lgaName , order );
        
        if (filteredData.size() > 0) {
            html += "<table class='border border-collapse border-blue-500 m-4'>";
            // Create table headers
            html += "<tr class='bg-blue-500 text-white'><th class='p-2'>Age Range</th><th class='p-2'>Count </th></tr>";
            for (ageCount at : filteredData) {
                // Add data to the table
                html += "<tr class='border'><td class='p-2'>" + at.getAgeCount() + "</td><td class='p-2'>" + at.getCount() + "</td></tr>";
            }
            html += "</table>";
        } else {
            html += "<h2 class='text-red-500 text-center mt-4'>No Data Found !!</h2>";
        }
        
        
    // Footer
    }else if(context.formParams("health_condition")!= null && context.formParams("ageFilter")!= null && context.formParams("indigenous_status") != null ){
        String age = context.formParam("ageFilter");
        String health_condition = context.formParam("health_condition");
        String indigenous_status = context.formParam("indigenous_status");
        String order = context.formParam("order");
        String lgaName = context.formParam("LgaName");
        String sortField = context.formParam("sortField");
        ArrayList<conditionCount> filteredData = jdbc.FilterConditionCount(sortField, age, indigenous_status , lgaName , order , health_condition);
        if (filteredData.size() > 0) {
            html += "<table class='border border-collapse border-blue-500 m-4'>";
            // Create table headers
            html += "<tr class='bg-blue-500 text-white'><th class='p-2'>Health Condition</th><th class='p-2'>Count </th></tr>";
            for (conditionCount ct : filteredData) {
                // Add data to the table
                html += "<tr class='border'><td class='p-2'>" + ct.getHealthCondition() + "</td><td class='p-2'>" + ct.getCount() + "</td></tr>";
            }
            html += "</table>";
        } else {
            html += "<h2 class='text-red-500 text-center mt-4'>No Data Found !!</h2>";
        }


    }
    html += "</div>";
    // Footer
    html = html + """ 
        <footer class="footer">
        <div class="container">
            <p class="text-center">© 2023 Voice to Parliament. All rights reserved.</p>
        </div>
    </footer>
    """;

    // Finish the HTML webpage
    html = html + "</body>" + "</html>";

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
  }
}