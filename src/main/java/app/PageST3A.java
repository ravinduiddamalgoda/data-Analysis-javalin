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
        html += "<script src='https://cdn.tailwindcss.com'></script>";
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
                "        var genderCheckboxes = document.querySelectorAll('genderFilter').value;\r\n" + //
                "        if (genderCheckboxes.length === 0) {\r\n" + //
                "            alert('Please select at least one gender.');\r\n" + //
                "            return false;\r\n" + //
                "        }\r\n" + //
                "\r\n" + //
                "        return true; // Form is valid, allow submission\r\n" + //
                "    }\r\n" + //
                "</script>\r\n" + //
                "\r\n" ;

                html += "<label for='ageFilter' class='block text-sm font-medium text-blue-500'>Select Age Range:</label>";
                html += "<select name='ageFilter' id='ageFilter' class='block mt-1 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-indigo-200 focus:border-indigo-300 sm:text-sm'>";
                html += "<option value='Default' selected>Default</option>\r\n";
                html += "<option value='0_4'>0-4 years</option>";
                html += "<option value='5_9'>5-9 years</option>";
                html += "<option value='10_14'>10-14 years</option>";
                html += "<option value='15_19'>15-19 years</option>";
                html += "<option value='20_24'>20-24 years</option>";
                html += "<option value='25_29'>25-29 years</option>";
                html += "<option value='30_34'>30-34 years</option>";
                html += "<option value='35_39'>35-39 years</option>";
                html += "<option value='40_44'>40-44 years</option>";
                html += "<option value='45_49'>45-49 years</option>";
                html += "<option value='50_54'>50-54 years</option>";
                html += "<option value='55_59'>55-59 years</option>";
                html += "<option value='60_64'>60-64 years</option>";
                html += "<option value='65+'>65+</option>";
                html += "</select>";
                

            html +="        <div class='mb-4'>\r\n" + //
            "            <label for='indigenous_status' class='text-blue-500'>Indigenous Status:</label>\r\n" + //
            "            <select name='indigenous_status' id='indigenous_status' class='bg-blue-100 p-2 rounded'>\r\n" + //
            "                <option value='Indigenous'>Indigenous</option>\r\n" + //
            "                <option value='NonIndigenous'>Non-Indigenous</option>\r\n" + //
            "                <option value='NotStated'>Not stated</option>\r\n" + //
            "            </select>\r\n" + //
            "        </div>\r\n";
    
            html += "<label for='genderFilter' class='block text-sm font-medium text-blue-500'>Gender Filter:</label><br>";
            html += "<select id='genderFilter' name='genderFilter' class='block mt-1 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-indigo-200 focus:border-indigo-300 sm:text-sm'>";
            html += "<option value='all'>All</option>";
            html += "<option value='male'>Male</option>";
            html += "<option value='female'>Female</option>";
            html += "</select>";


            html += "        <div class='mb-4'>\r\n" + //
                    "            <label for='year' class='text-blue-500'>Enter the year:</label>\r\n" + //
                    "            <select name='year' id='sortField' class='bg-blue-100 p-2 rounded'>\r\n" + //
                    "                <option value='2016'>2016</option>\r\n" + //
                    "                <option value='2021'>2021</option>\r\n" + //
                    "            </select>\r\n" + //
                    "        </div>\r\n";

                    html += "<input type='submit' class='bg-blue-500 text-white p-2 rounded cursor-pointer' value='Apply Filters'>";

                html += "</form> ";

        
            
                boolean ageData = false;
                String validationData =  context.formParam("ageFilter");
                if(validationData != null && !validationData.equals("Default")){
                    ageData = true;
                }
        // Close Content div
        html = html + "</div>";
        if (context.formParams("ageFilter") != null && context.formParams("genderFilter") != null && context.formParams("year") != null ) {
            String age = context.formParam("ageFilter");
            String year = context.formParam("year");
            String gender = context.formParam("genderFilter");
            System.out.println("Function Called");
            System.out.println(gender);
            System.out.println(age);
            System.out.println(year);
            ArrayList<Education> filteredData = jdbc.getFilteredData(age, gender, year);
            
            if (filteredData.size() > 0) {
                html += "<table class='table-auto'>";
                // Create table headers
                html += "<tr class='bg-gray-200'><th class='px-4 py-2'>LGA Code</th><th class='px-4 py-2'>Year</th><th class='px-4 py-2'>Indigenous</th><th class='px-4 py-2'>Age Count</th><th class='px-4 py-2'>Condition Count</th></tr>";
                for (Education education : filteredData) {
                    // Add data to the table
                    html += "<tr class='border'><td class='px-4 py-2'>" + education.getColumn1() + "</td><td class='px-4 py-2'>" + education.getColumn2() + "</td><td class='px-4 py-2'>" +
                            education.getColumn3() + "</td><td class='px-4 py-2'>" + education.getColumn4() + "</td><td class='px-4 py-2'>" + education.getColumn5() + "</td></tr>";
                }
                html += "</table>";
            } else {
                html += "<h2 class='text-red-500'>No Data Found !!</h2>";
            }
        }
        
        // Footer
        html += "<div class='footer bg-gray-200 text-center py-2 mt-4'>Education Completion of States and LGA's According to Census Data</div>";
        
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

