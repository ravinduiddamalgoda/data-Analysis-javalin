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
        html = html + "<div class='content'>";
        html = html + "<div class='contentTWOB'>";
/*
 View Information by Individual LGAs with raw values
 */

        html = html + "<form action='/page2B.html' method='post'>";
        
        html = html + "<h2>View By LGAs or States</h2>";
            html = html + "<input type='radio' name = 'LGAorStates' value = 'LGAs' id = 'LGAs'/>";
            html = html + "<label for = 'LGAs'>LGAs</label>";
            
            html = html + "<input type='radio' name = 'LGAorStates' value = 'States' id = 'States'/>";
            html = html + "<label for = 'States'>States</label>";
           
            boolean proceed = true;
            String LGAorStates = context.formParam("LGAorStates");
            if (LGAorStates == null) {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                proceed = false;
            }

/*
 View Information by either school or non school completion with raw values
 */

        html = html + "<h2>View By Education</h2>";
            html = html + "<input type='radio' name = 'SchoolorNonSchool' value = 'School' id = 'School'/>";
            html = html + "<label for = 'School'>School</label>";
            
            html = html + "<input type='radio' name = 'SchoolorNonSchool' value = 'Non School' id = 'Non School'/>";
            html = html + "<label for = 'Non School'>Non School</label>";

            String SchoolorNonSchool = context.formParam("SchoolorNonSchool");
            if (SchoolorNonSchool == null) {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                proceed = false;
            }
 
/*
 View Information by census year with raw values
 */

        html = html + "<h2>View By Year</h2>";
            html = html + "<input type='radio' name = 'year' value = '2016' id = '2016'/>";
            html = html + "<label for = '2016'>2016</label>";

            html = html + "<input type='radio' name = 'year' value = '2021' id = '2021'/>";
            html = html + "<label for = '2021'>2021</label>";

            String year = context.formParam("year");
            if (year == null) {
                // If NULL, nothing to show, therefore we make some "no results" HTML
                html = html + "<h4><i>Fill in Fields</i></h4>";
                proceed = false;
            }
        
            // sort by Column
            html = html + "<h2>Sort by Column</h2>";
            html = html + "<label for = 'columnChoose'>Sort By a Column</label><br>";
            html = html + "<select name = 'columnChoose' id = 'columnChoose'";
                html = html + "<option value =''></option>'"
                 + "<option value ='LGAcode'>LGA or State</option>'"
                 + "<option value ='Name'>Name</option>'"
                 + "<option value ='col2'>Column 1</option>'"
                 + "<option value ='col3'>Column 2</option>'"
                 + "<option value ='col4'>Column 3</option>'"
                 + "<option value ='col5'>Column 4</option>'"
                 + "<option value ='col6'>Column 5</option>'"
                 + "<option value ='col7'>Column 6</option>'";

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
            html = html + "<h2>Sort by Ascending or Descending</h2>";

            html = html + "<select name = 'AscDesc' id = 'AscDesc'";
                html = html + "<option value =''></option>'"
                 + "<option value ='ASC'>Descending</option>'"
                 + "<option value ='DESC'>Ascending</option>'";

            html = html + "</select>";


            if(context.formParam("AscDesc") == null){Order = "ASC";}
            else{Order = context.formParam("AscDesc");}
            
            
            
            
            html = html + "<h2>View Raw Values or as Percentage</h2>";

            html = html + "<select name = 'total' id = 'total";
                html = html + "<option value =''></option>'"
                 + "<option value ='true'>Raw Values</option>'"
                 + "<option value ='false'>Proportional Values(%)</option>'";

            html = html + "</select>";

            String totpropop = "";


            html = html + "<h2>Include Gender</h2>";

            html = html + "<select name = 'sex' id = 'sex";
                html = html + "<option value =''></option>'"
                 + "<option value ='All'>All</option>'"
                 + "<option value ='F'>Female</option>'"
                 + "<option value ='M'>Male</option>'";

            html = html + "</select>";

            String Sex = context.formParam("sex");
            while(Sex == null){Sex = "All";}
            if(Sex.equals("All")){
                Sex = "";
            }
            else{
                Sex = " and sc.Sex = '" + Sex + "'";
            }

            html = html + "<h2>Include Indigenous Status</h2>";

            html = html + "<select name = 'IndigStat' id = 'IndigStat";
                html = html + "<option value =''></option>'"
                 + "<option value ='All'>All</option>'"
                 + "<option value ='Indigenous'>Indigenous</option>'"
                 + "<option value ='NonIndigenous'>Non Indigenous</option>'"
                 + "<option value ='NotStated'>Not Stated</option>'";

            html = html + "</select>";

            String IndigStat = context.formParam("IndigStat");
            while(IndigStat == null){IndigStat = "All";}
            if(IndigStat.equals("All")){
                IndigStat = "";
            }
            else{
                IndigStat = " and sc.Indigenous_Status = '" + IndigStat + "'";
            }

            


        html = html + "<p></p><button type='submit'>View Information</button>";

        html = html+ "</form>";
        if(context.formParam("total") == null){totpropop = "true";}
        else {totpropop = context.formParam("total");}

   
// Title table
    ArrayList<Education> edu;
    if(proceed && SchoolorNonSchool.equals("School")){
        html = html + "<div class = 'education'><h2>" + SchoolorNonSchool + " completion level according to the " + year + " census listed by " + LGAorStates +"</h2>";
        edu = jdbc.EducationbyLGA(LGAorStates, SchoolorNonSchool, year, sortColumn, Order,totpropop, IndigStat, Sex);
// Create table
        html = html + "<table class = 'DataShow'><tr>";
        html = html + "<td>" + LGAorStates + "</td>" + 
        "<td>" + "#" + "</td>" + 
        "<td>" + "Did Not Attend" + "</td>"+ 
        "<td>" + "Year 8 and Below" + "</td>"+ 
        "<td>" + "Year 9 or Equivalent" + "</td>"+ 
        "<td>" + "Year 10 or Equivalent" + "</td>"+ 
        "<td>" + "Year 11 or Equivalent" + "</td>"+ 
        "<td>" + "Year 12 or Equivalent" + "</td>"
        + "</tr>";
        
        
        int j = 1;
        
        for(int k = 0; k < edu.size(); k +=6){
            Education education = edu.get(k);

            if(LGAorStates.equals("LGAs")){
                html = html + "<tr><td>" + education.getColumn3() + "</td>";
            } else{
                html = html + "<tr><td>" + education.getColumn2() + "</td>";
            }
            html = html + "<td>" + j + "</td>";


            for(int i = 0; i < 6; i++){
                Education education2 = edu.get(i+k);
                html = html + "<td>" + education2.getColumn5() + "</td>";
            }
            html = html + "</tr>";
            j++;
        }
        html = html + "</table>";
    }


        else if (proceed){
        html = html + "<div class = 'education'><h2>" + SchoolorNonSchool + " completion level according to the " + year + " census listed by " + LGAorStates +"</h2>";
        edu = jdbc.EducationbyLGA(LGAorStates, SchoolorNonSchool, year, sortColumn, Order,totpropop, IndigStat, Sex);
// Create table
        html = html + "<table class = 'School'><tr>";
        html = html + "<td>" + LGAorStates + "</td>" + 
        "<td>" + "#" + "</td>"+ 
        "<td>" + "Advanced Diploma and Diploma Level" + "</td>"+ 
        "<td>" + "Bachelor Degree Level" + "</td>"+ 
        "<td>" + "Certificate I & II Level" + "</td>"+ 
        "<td>" + "Certificate III & IV Level" + "</td>"+ 
        "<td>" + "Postgraduate Degree Level, Graduate Diploma and Graduate Certificate Level" + "</td>"
        + "</tr>";

        int j = 1;
        
        for(int k = 0; k < edu.size(); k +=5){
            Education education = edu.get(k);

            if(LGAorStates.equals("LGAs")){
                html = html + "<tr><td>" + education.getColumn3() + "</td>";
            } else{
                html = html + "<tr><td>" + education.getColumn2() + "</td>";
            }
            html = html + "<td>" + j + "</td>";


            for(int i = 0; i < 5; i++){
                Education education2 = edu.get(i+k);
                html = html + "<td>" + education2.getColumn5() + "</td>";
            }
            html = html + "</tr>";
            j++;
        }
        html = html + "</table>";
    }

      
        
        
        
        // Close Content div
        html = html + "</div>";
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <p>Education Completion of States and LGA's According to Census Data</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);

    }


}
