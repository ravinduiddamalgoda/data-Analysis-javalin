package app;

import java.util.ArrayList;
import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";
        JDBCConnection jdbc = new JDBCConnection(); 
        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

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
                <h1>Subtask 3.A</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <p>Subtask 3.A page content</p>
            """;

        html =  html + "<script>\r\n" + //
                "    function validateForm() {\r\n" + //
                "        // Check if an age range is selected\r\n" + //
                "        var ageFilter = document.getElementById('ageFilter').value;\r\n" + //
                "        if (ageFilter === '') {\r\n" + //
                "            alert('Please select an age range.');\r\n" + //
                "            return false;\r\n" + //
                "        }\r\n" + //
                "\r\n" + //
                "        // Check if at least one gender option is checked\r\n" + //
                "        var genderCheckboxes = document.querySelectorAll('input[name=\"genderFilter\"]:checked');\r\n" + //
                "        if (genderCheckboxes.length === 0) {\r\n" + //
                "            alert('Please select at least one gender.');\r\n" + //
                "            return false;\r\n" + //
                "        }\r\n" + //
                "\r\n" + //
                "        return true; // Form is valid, allow submission\r\n" + //
                "    }\r\n" + //
                "</script>\r\n" + //
                "\r\n" ;

            html = html +"<form action='/page3A.html' method='post' onsubmit='return validateForm();'>\r\n" + //
                "    <!-- Age Filter -->\r\n" + //
                "    <label for='ageFilter'>Select Age Range:</label>\r\n" + //
                "    <select name='ageFilter' id='ageFilter'>\r\n" + //
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
                "\r\n" + //
                "    <!-- Gender Filter -->\r\n" + //
                "    <label>Gender Filter:</label><br>\r\n" + //
                "    <input type='checkbox' name='genderFilter' value='male'> Male\r\n" + //
                "    <input type='checkbox' name='genderFilter' value='female'> Female\r\n" + //
                "    <!-- Add more gender options as needed -->\r\n" + //
                "    <br><br>\r\n" + //
                "\r\n";
            html += "";

            html +=    "    <button type='submit'>Apply Filters</button>\r\n" + //
                "</form> ";

            

        // Close Content div
        html = html + "</div>";
        if (context.formParam("ageFilter") != null && context.formParams("genderFilter") != null) {
            String age = context.formParam("ageFilter");
            // List<String> gender = context.formParams("genderFilter");
            String gender = context.formParam("genderFilter");
            ArrayList<Education> filteredData = jdbc.getFilteredData(age, gender);
            if(filteredData.size() > 0){
                html += "<table>";
            // Create table headers
            html += "<tr><th>LGA Code</th><th>Year</th><th>Indigenous</th><th>Age Count</th><th>Condition Count</th></tr>";
            for (Education education : filteredData) {
                // Add data to the table
                html += "<tr><td>" + education.getColumn1() + "</td><td>" + education.getColumn2() + "</td><td>" +
                        education.getColumn3() + "</td><td>" + education.getColumn4() + "</td><td>" + education.getColumn5() + "</td></tr>";
            }
            html += "</table>";
            }else {
                html += "<h2>No Data Found !!</h2>"; 
            }
            
        // Footer
        }
        html = html + """
            <div class='footer'>
                <p>COSC2803 - Studio Project Starter Code (Sep23)</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    }

