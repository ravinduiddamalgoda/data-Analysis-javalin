package app;

import java.util.ArrayList;

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
/*
 The *first digit* of the LGA code provides the State or Territory that the LGA is located within. These codes are:
 1 - NSW
 2 - Victoria
 3 - QLD
 4 - South Australia
 5 - Western Australia
 6 - Tasmania
 7 - Northern Territory
 8 - ACT
 9 - Other Australian Territories, including Offshore Island Territories, Jervis Bay Territory, and Australian Antarctic Territory.
 */

public class PageST2B implements Handler {


    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.2</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<script src=\"https://cdn.tailwindcss.com\"></script>";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";
        JDBCConnection jdbc = new JDBCConnection(); 

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Homepage</a>
                <a href='mission.html'>Our Mission</a>
                <a href='page2A.html'>Sub Task 2.A</a>
                <a href='page2B.html'>Education</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Find SImilar LGAs</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Education Level</h1>
            </div>
        """;

                // Add Div for page Content
            html = html + "<div class='content bg-white p-4 rounded-lg shadow-md'>";
            html = html + "<div class='contentTWOB'>";

            // View Information by Individual LGAs with raw values
            html = html + "<form action='/page2B.html' method='post' class='mt-4'>";
            html = html + "<h2 class='text-xl font-bold'>View By LGAs or States</h2>";
            html = html + "<div class='flex items-center space-x-4'>";
            html = html + "<input type='radio' name='LGAorStates' value='LGAs' id='LGAs' class='text-blue-500'";
            html = html + "<label for='LGAs'>LGAs</label>";
            html = html + "<input type='radio' name='LGAorStates' value='States' id='States' class='text-blue-500'";
            html = html + "<label for='States'>States</label>";
            html = html + "</div>";

           
            boolean proceed = true;
            String LGAorStates = context.formParam("LGAorStates");
            if (LGAorStates == null) {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                proceed = false;
            }

/*
 View Information by either school or non school completion with raw values
 */

            html = html + "<h2 class='text-xl font-bold'>View By Education</h2>";
            html = html + "<div class='flex items-center space-x-4'>";
            html = html + "<input type='radio' name='SchoolorNonSchool' value='School' id='School' class='text-blue-500'";
            html = html + "<label for='School'>School</label>";
            html = html + "<input type='radio' name='SchoolorNonSchool' value='Non School' id='Non School' class='text-blue-500'";
            html = html + "<label for='Non School'>Non School</label>";
            html = html + "</div>";
 
            String SchoolorNonSchool = context.formParam("SchoolorNonSchool");
            if (SchoolorNonSchool == null) {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                proceed = false;
            }
 
/*
 View Information by census year with raw values
 */

            html = html + "<h2 class='text-xl font-bold'>View By Year</h2>";
            html = html + "<div class='flex items-center space-x-4'>";
            html = html + "<input type='radio' name='year' value='2016' id='2016' class='text-blue-500'";
            html = html + "<label for='2016'>2016</label>";
            html = html + "<input type='radio' name='year' value='2021' id='2021' class='text-blue-500'";
            html = html + "<label for='2021'>2021</label>";
            html = html + "</div>";
            

            String year = context.formParam("year");
            if (year == null) {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                html = html + "<h4 class='text-red-500 italic'>Fill in Fields</h4>";

                proceed = false;
            }
        
            // sort by Column
            html = html + "<h2 class='text-xl font-bold'>Sort by Column</h2>";
            html = html + "<label for='columnChoose' class='text-gray-600'>Sort By a Column</label><br>";
            html = html + "<select name='columnChoose' id='columnChoose' class='p-2 rounded-md border border-gray-300'>";
            html = html + "<option value='' class='text-gray-400'></option>";
            html = html + "<option value='LGAcode' class='text-blue-500'>LGA or State</option>";
            html = html + "<option value='Name' class='text-blue-500'>Name</option>";
            html = html + "<option value='col2' class='text-blue-500'>Column 1</option>";
            html = html + "<option value='col3' class='text-blue-500'>Column 2</option>";
            html = html + "<option value='col4' class='text-blue-500'>Column 3</option>";
            html = html + "<option value='col5' class='text-blue-500'>Column 4</option>";
            html = html + "<option value='col6' class='text-blue-500'>Column 5</option>";
            html = html + "<option value='col7' class='text-blue-500'>Column 6</option>";
            html = html + "</select>";
            
            String sortColumn = context.formParam("columnChoose");
            
            if (context.formParam("columnChoose") == null){
                sortColumn = "LGAcode";
            }

            while(sortColumn.equals("Name") && LGAorStates.equals("States")){
                sortColumn = "stateAbbr";
            }

            while(sortColumn.equals("Name")){
                sortColumn = "name"; 
            }

            String Order = "ASC";
            // Ascending or Descending
            html = html + "<h2 class='text-xl font-bold'>Sort by Ascending or Descending</h2>";
            html = html + "<select name='AscDesc' id='AscDesc' class='p-2 rounded-md border border-gray-300'>";
            html = html + "<option value='' class='text-gray-400'></option>";
            html = html + "<option value='ASC' class='text-blue-500'>Descending</option>";
            html = html + "<option value='DESC' class='text-blue-500'>Ascending</option>";
            html = html + "</select>";



            if(context.formParam("AscDesc") == null){Order = "ASC";}
            else{Order = context.formParam("AscDesc");}
            
            
            
            
            html = html + "<h2 class='text-xl font-bold'>View Raw Values or as Percentage</h2>";
            html = html + "<select name='total' id='total' class='p-2 rounded-md border border-gray-300'>";
            html = html + "<option value='' class='text-gray-400'></option>";
            html = html + "<option value='true' class='text-blue-500'>Raw Values</option>";
            html = html + "<option value='false' class='text-blue-500'>Proportional Values(%)</option>";
            html = html + "</select>";


            String totpropop = "";


            html = html + "<h2 class='text-xl font-bold'>Include Gender</h2>";
            html = html + "<select name='sex' id='sex' class='p-2 rounded-md border border-gray-300'>";
            html = html + "<option value='' class='text-gray-400'></option>";
            html = html + "<option value='All' class='text-blue-500'>All</option>";
            html = html + "<option value='F' class='text-blue-500'>Female</option>";
            html = html + "<option value='M' class='text-blue-500'>Male</option>";
            html = html + "</select>";


            String Sex = context.formParam("sex");
            while(Sex == null){Sex = "All";}
            if(Sex.equals("All")){
                Sex = "";
            }
            else{
                Sex = " and sc.Sex = '" + Sex + "'";
            }

            html = html + "<h2 class='text-xl font-bold'>Include Indigenous Status</h2>";
            html = html + "<select name='IndigStat' id='IndigStat' class='p-2 rounded-md border border-gray-300'>";
            html = html + "<option value='' class='text-gray-400'></option>";
            html = html + "<option value='All' class='text-blue-500'>All</option>";
            html = html + "<option value='Indigenous' class='text-blue-500'>Indigenous</option>";
            html = html + "<option value='NonIndigenous' class='text-blue-500'>Non Indigenous</option>";
            html = html + "<option value='NotStated' class='text-blue-500'>Not Stated</option>";
            html = html + "</select>";
            

            String IndigStat = context.formParam("IndigStat");
            while(IndigStat == null){IndigStat = "All";}
            if(IndigStat.equals("All")){
                IndigStat = "";
            }
            else{
                IndigStat = " and sc.Indigenous_Status = '" + IndigStat + "'";
            }

            


            html = html + "<div class='p-4'>"; // Add padding around the button
            html = html + "<button type='submit' class='bg-blue-500 text-white rounded-md p-2'>View Information</button>";
            html = html + "</div>";
            html = html + "</form>";
            
        if(context.formParam("total") == null){totpropop = "true";}
        else {totpropop = context.formParam("total");}

   
// Title table
    ArrayList<Education> edu;
    if(proceed && SchoolorNonSchool.equals("School")){
        html = html + "<div class='education text-xl font-semibold mt-4'>" + SchoolorNonSchool + " completion level according to the " + year + " census listed by " + LGAorStates + "</div>";
        edu = jdbc.EducationbyLGA(LGAorStates, SchoolorNonSchool, year, sortColumn, Order, totpropop, IndigStat, Sex);
        
        // Create table
        html = html + "<table class='DataShow w-full border-collapse border border-gray-300 mt-4'>";
        html = html + "<thead>";
        html = html + "<tr class='bg-gray-200'>";
        html = html + "<th class='border border-gray-300 p-2'>" + LGAorStates + "</th>";
        html = html + "<th class='border border-gray-300 p-2'>#</th>";
        html = html + "<th class='border border-gray-300 p-2'>Did Not Attend</th>";
        html = html + "<th class='border border-gray-300 p-2'>Year 8 and Below</th>";
        html = html + "<th class='border border-gray-300 p-2'>Year 9 or Equivalent</th>";
        html = html + "<th class='border border-gray-300 p-2'>Year 10 or Equivalent</th>";
        html = html + "<th class='border border-gray-300 p-2'>Year 11 or Equivalent</th>";
        html = html + "<th class='border border-gray-300 p-2'>Year 12 or Equivalent</th>";
        html = html + "</tr>";
        html = html + "</thead>";
        
        
        int j = 1;
        
        for (int k = 0; k < edu.size(); k += 6) {
            Education education = edu.get(k);
        
            if (LGAorStates.equals("LGAs")) {
                html = html + "<tr class='border border-gray-300'>";
            } else {
                html = html + "<tr class='border border-gray-300'>";
            }
            html = html + "<td class='border border-gray-300 p-2'>" + education.getColumn3() + "</td>";
            html = html + "<td class='border border-gray-300 p-2'>" + j + "</td>";
        
            for (int i = 0; i < 6; i++) {
                Education education2 = edu.get(i + k);
                html = html + "<td class='border border-gray-300 p-2'>" + education2.getColumn5() + "</td>";
            }
            html = html + "</tr>";
            j++;
        }
        html = html + "</table>";
        
    }


        else if (proceed){
            html = html + "<div class='education'><h2 class='text-xl font-semibold'>" + SchoolorNonSchool + " completion level according to the " + year + " census listed by " + LGAorStates + "</h2>";
            edu = jdbc.EducationbyLGA(LGAorStates, SchoolorNonSchool, year, sortColumn, Order, totpropop, IndigStat, Sex);
            html = html + "<div class='overflow-x-auto'>";
            html = html + "<table class='min-w-full divide-y divide-gray-200'>";
            html = html + "<thead class='bg-gray-50'>";
            html = html + "<tr>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>" + LGAorStates + "</th>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>#</th>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>Advanced Diploma and Diploma Level</th>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>Bachelor Degree Level</th>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>Certificate I & II Level</th>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>Certificate III & IV Level</th>";
            html = html + "<th scope='col' class='px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider'>Postgraduate Degree Level, Graduate Diploma and Graduate Certificate Level</th>";
            html = html + "</tr>";
            html = html + "</thead>";
            html = html + "<tbody class='bg-white divide-y divide-gray-200'>";

            int j = 1;

            for (int k = 0; k < edu.size(); k += 5) {
                Education education = edu.get(k);
            
                html = html + "<tr class='" + (j % 2 == 0 ? "bg-gray-50" : "bg-white") + "'>";
                html = html + "<td class='px-6 py-4 whitespace-nowrap'>" + education.getColumn3() + "</td>";
                html = html + "<td class='px-6 py-4 whitespace-nowrap'>" + education.getColumn2() + "</td>";
            
                for (int i = 0; i < 5; i++) {
                    Education education2 = edu.get(i + k);
                    html = html + "<td class='px-6 py-4 whitespace-nowrap'>" + education2.getColumn5() + "</td>";
                }
                html = html + "</tr>";
                j++;
            }
            html = html + "</table>";
            
    }

      
        
        
        
        // Close Content div
        // Close Content div
        html = html + "</div>";
        html = html + "</div>";  // Close contentTWOB div

        // Footer
        html = html + "<footer class='bg-gray-300 p-4 text-center text-sm text-gray-600'>";
        html = html + "Education Completion of States and LGA's According to Census Data";
        html = html + "</footer>";

        // Finish the HTML webpage
        html = html + "</body>";
        html = html + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);

    }


}
