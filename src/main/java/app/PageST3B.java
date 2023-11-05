package app;

import java.util.ArrayList;

import javax.xml.crypto.Data;

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
public class PageST3B implements Handler {

// Add Fields
private String LGAcode;
private String numSimilarLGAs;
private String DataSet;
private String year;
private String specificConstraint;
private String IndigenousStat;
private String Gender;
private boolean proceed = true;
private boolean allFields = false;
private boolean reset = false;

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Reset
        JDBCConnection jdbc = new JDBCConnection(); 
        if(reset){
            LGAcode = null;
            numSimilarLGAs = null;
            DataSet = null;
            year = null;
            specificConstraint = null;
            IndigenousStat = null;
            Gender = null;
            allFields = false;
            proceed = false;
            reset = false;
        }

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Find Similar LGAs</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html += "<script src=\"https://cdn.tailwindcss.com\"></script>";
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
                <a href='page2B.html'>Education</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Find SImilar LGAs</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Find Similar LGAs</h1>
            </div>
        """;

        


                
        // Start Form
        html = html + "<div class='content'>";
        html = html + "<div class='contentTHREEB'>";

        // Start Form
        html += "<form action='/page3B.html' method='post' class='bg-white p-4 shadow-md rounded-md'>";

        // Select Data Set
        if (DataSet == null) {
            html += "<div class='mb-4'><label for='DataChoose' class='block text-gray-700 text-sm font-bold mb-2'>Choose Data Set to View:</label>";
            html += "<select name='DataChoose' id='DataChoose' class='block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50'>";
            html += "<option value='AgeDemographics'>Age Demographics</option>" +
                    "<option value='MedicalConditions'>Long Term Health Conditions</option>" +
                    "<option value='SchoolCompletion'>School Completion Level</option>" +
                    "<option value='NonSchoolCompletion'>Non School Completion Level</option>";
            html += "</select></div>";
        } else {
            html += "<label for='DataChoose' class='block text-gray-700 text-sm font-bold mb-2'>Selected Data Set to View: " + DataSet + "</label>";
        }
        if (context.formParam("DataChoose") != null && DataSet == null) {
            DataSet = context.formParam("DataChoose");
            proceed = true;
        } else if (DataSet == null) {
            proceed = false;
        }
        
        html += "<button type='submit' class='bg-blue-500 text-white font-bold py-2 px-4 rounded-md'>View Information</button>";
        html += "</form>";
        
        // Start Form
        if (DataSet != null) {
            html += "<form action='/page3B.html' method='post' class='bg-white p-4 shadow-md rounded-md'>";
        
            // Select LGA
            if (LGAcode == null) {
                html += "<div class='mb-4'>";
                html += "<label for='LGACsort' class='block text-gray-700 text-sm font-bold mb-2'>Choose LGA Code to Investigate:</label>";
                html += "<input type='number' id='LGACsort' name='LGACsort' class='block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50'>";
                html += "</div>";
            } else {
                html += "<label for='LGACsort' class='block text-gray-700 text-sm font-bold mb-2'>Chosen LGA Code to Investigate: " + LGAcode + "</label>";
            }
        
            // You can add the "OR" section for "LGANsort" in a similar manner if needed.
        
            if (context.formParam("LGACsort") != null) {
                LGAcode = context.formParam("LGACsort");
                proceed = true;
            } else if (LGAcode == null) {
                proceed = false;
            }



        

/*
 * Select Year
 */
if (!DataSet.equals("MedicalConditions")) {
    if (year == null) {
        html += "<label for='YearChoose' class='block text-gray-700 text-sm font-bold mb-2'>Choose Year to View By:</label>";
        html += "<div class='mb-4'>";
        html += "<input type='radio' name='year' id='2016' value='2016' class='mr-1'>";
        html += "<label for='2016' class='text-gray-600 font-medium'>2016</label>";
        html += "<input type='radio' name='year' id='2021' value='2021' class='ml-4 mr-1'>";
        html += "<label for='2021' class='text-gray-600 font-medium'>2021</label>";
        html += "</div>";
    } else {
        html += "<label for='YearChoose' class='block text-gray-700 text-sm font-bold mb-2'>Selected Year to View By: " + year + "</label><p></p>";
    }

    if (context.formParam("year") != null) {
        year = context.formParam("year");
        proceed = true;
    } else if (year == null) {
        proceed = false;
    }
}


        

/*
 * Select Number of Similar LGAs
        */
        if (numSimilarLGAs == null) {
            html += "<label for='LGAnum' class='block text-gray-700 text-sm font-bold mb-2'>Choose the Number of LGAs to Return:</label>";
            html += "<input type='number' id='LGAnum' name='LGAnum' class='w-full border rounded px-3 py-2 mt-2'>";
            html += "<p></p>";
        } else {
            html += "<label for='LGAnum' class='block text-gray-700 text-sm font-bold mb-2'>Chosen Number of LGAs to Return: " + numSimilarLGAs + "</label><p></p>";
        }

        if (context.formParam("LGAnum") != null) {
            numSimilarLGAs = context.formParam("LGAnum");
            proceed = true;
        } else if (numSimilarLGAs == null) {
            proceed = false;
        }


        

/*
 * Select Constraints
 */
    // Indigenous Stat
    if (IndigenousStat == null) {
        html += "<label for='indigenousStat' class='block text-gray-700 text-sm font-bold mb-2'>Choose Indigenous Status:</label>";
        html += "<select name='indigenousStat' id='indigenousStat' class='w-full border rounded px-3 py-2 mt-2'>";
        html += "<option value='All'>All</option>";
        html += "<option value='Indigenous'>Indigenous</option>";
        html += "<option value='NonIndigenous'>Non Indigenous</option>";
        html += "<option value='NotStated'>Not Stated</option>";
        html += "</select><br></br>";
    } else {
        html += "<label for='indigenousStat' class='block text-gray-700 text-sm font-bold mb-2'>Selected Indigenous Status: " + IndigenousStat + "</label><br></br>";
    }
    
    if (context.formParam("indigenousStat") != null) {
        IndigenousStat = context.formParam("indigenousStat");
        proceed = true;
    } else if (IndigenousStat == null) {
        IndigenousStat = "All";
    }
    

    // Gender
    if (Gender == null) {
        html += "<label for='Gender' class='block text-gray-700 text-sm font-bold mb-2'>Choose Gender:</label>";
        html += "<select name='Gender' id='Gender' class='w-full border rounded px-3 py-2'>";
        html += "<option value='All'>All</option>";
        html += "<option value='M'>Male</option>";
        html += "<option value='F'>Female</option>";
        html += "</select>";
    } else {
        html += "<label for='Gender' class='block text-gray-700 text-sm font-bold mb-2'>Selected Gender: " + Gender + "</label>";
    }
    
    if (context.formParam("Gender") != null) {
        Gender = context.formParam("Gender");
        proceed = true;
    } else if (Gender == null) {
        Gender = "All";
    }
    


        // Specific to Dataset
   if(DataSet != null){
    //Give Age Demographic Range
        if(DataSet.equals("AgeDemographics")){
            html += "<p><label for='AgeDemo' class='block text-gray-700 text-sm font-bold mb-2'>Age Range:</label>"
            + "<div class='flex'>";
        html += "<input type='number' id='AgeDemoMin' name='AgeDemoMin' class='w-1/2 border rounded-l px-3 py-2' placeholder='Min'>";
        html += "<input type='number' id='AgeDemoMax' name='AgeDemoMax' class='w-1/2 border rounded-r px-3 py-2' placeholder='Max'>";
        html += "</div></p>";
            proceed = true;
        }



    //Give School Completion Range
        else if(DataSet.equals("SchoolCompletion")){
            html += "<p><label for='SchoolRange' class='block text-gray-700 text-sm font-bold mb-2'>School Year(8-12):</label>"
        + "<div class='flex'>";
    html += "<input type='number' id='SchoolYMin' name='SchoolYMin' class='w-1/2 border rounded-l px-3 py-2' placeholder='Min'>";
    html += "<input type='number' id='SchoolYMax' name='SchoolYMax' class='w-1/2 border rounded-r px-3 py-2' placeholder='Max'>";
    html += "</div></p>";
            proceed = true;

        }

    //Give Medical Conditions Options
        else if(DataSet.equals("MedicalConditions")){
            if(specificConstraint == null){
                html += "<p class='mb-2'><label for='MedCon' class='block text-gray-700 text-sm font-bold mb-2'>Long Term Medical Condition:</label>"
                + "<select name='MedCon' id='MedCon' class='w-full border rounded px-3 py-2 text-gray-700'>";
            html += "<option value='All'>All</option>"
                + "<option value='Arthritis'>Arthritis</option>"
                + "<option value='Asthma'>Asthma</option>"
                + "<option value='Cancer'>Cancer</option>"
                + "<option value='Dementia'>Dementia</option>"
                + "<option value='Diabetes'>Diabetes</option>"
                + "<option value='HeartDisease'>Heart Disease</option>"
                + "<option value='KidneyDisease'>Kidney Disease</option>"
                + "<option value='LungCondition'>Lung Condition</option>"
                + "<option value='MentalHealth'>Mental Health</option>"
                + "<option value='Other'>Other</option>"
                + "<option value='Stroke'>Stroke</option>"
                + "</select>";
            }
            else{
                html += "<p><label for='MedCon' class='block text-gray-700 text-sm font-bold mb-2'>Long Term Medical Condition: " + specificConstraint + "</label>";
            }

            if(context.formParam("MedCon") != null){
                specificConstraint = context.formParam("MedCon");
                proceed = true;
            }
            else{
                specificConstraint = "All";
                proceed = true;
            } 
        }

    //Give Non School Completion Options
        else if(DataSet.equals("NonSchoolCompletion")){
            if(specificConstraint == null){
                html += "<p><label for='NonSchoolOpt' class='block text-sm font-medium text-gray-700'>Non School Completion:</label>";
                html += "<select name='NonSchoolOpt' id='NonSchoolOpt' class='mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm sm:leading-5'>";
                html += "<option value='All'>All</option>";
                html += "<option value='adip_dip'>Advanced Diploma and Diploma Level</option>";
                html += "<option value='bd'>Bachelor Degree Level</option>";
                html += "<option value='ct_i_ii'>Certificate I & II Level</option>";
                html += "<option value='ct_iii_iv'>Certificate III & IV Level</option>";
                html += "<option value='pd_gd_gc'>Postgraduate Degree and Graduate Level</option>";
                html += "</select>";

            }
            else{
                String NS;
                switch (specificConstraint){
                    case "adip_dip":
                        NS = "Advanced Diploma and Diploma Level";
                        break;
                    case "bd":
                        NS = "Bachelor Degree Level";
                        break;
                    case "ct_i_ii":
                        NS = "Certificate I & II Level";
                        break;
                    case "ct_iii_iv":
                        NS = "Certificate III & IV Level";
                        break;
                    case "pd_gd_gc":
                        NS = "Postgraduate Degree and Graduate Level";
                        break;
                    default:
                        NS = "All";
                        break;
                }

                html += "<p><label for = 'NonSchoolOpt'>Non School Completion: " + NS + "</label>";
            }

            if(context.formParam("NonSchoolOpt") != null){
                specificConstraint = context.formParam("NonSchoolOpt");
                proceed = true;
            }
            else{
                specificConstraint = "All";
                proceed = true;
            }  
        }

    }

   



// Reset Inputs
            html += "<p></p><input type='radio' name='Reset' id='Reset' value='reset' class='form-radio text-indigo-600 border-gray-300 shadow-sm focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm sm:leading-5'>";
            html += "<label for='Reset' class='ml-2 text-sm text-gray-700'>Reset Inputs</label>";


        if(context.formParam("Reset") != null){
            reset = true;
        }
// Submit Form
        html += "<p></p><button type='submit'>View Information</button>";

// Close Form
        html += "</form>";}

        
        
/* 
Collect Data 
Make Table
*/
if (proceed == true){
    ArrayList<Education> table;
    
// Table
    html = html + "<table class = 'DataShow'><tr>";
            int j = 1;
    switch(DataSet){
        case "SchoolCompletion":
            html = html + "<td>LGA Names</td>" + 
            "<td>" + "#" + "</td>" + 
            "<td>" + "Did Not Attend" + "</td>"+ 
            "<td>" + "Year 8 and Below" + "</td>"+ 
            "<td>" + "Year 9 or Equivalent" + "</td>"+ 
            "<td>" + "Year 10 or Equivalent" + "</td>"+ 
            "<td>" + "Year 11 or Equivalent" + "</td>"+ 
            "<td>" + "Year 12 or Equivalent" + "</td>"
            + "</tr>";
            break;
        
        case "MedicalConditions":
            table = jdbc.getMedical(LGAcode, numSimilarLGAs,IndigenousStat,Gender,specificConstraint);
            html = html + "<td>LGA Names</td>" + 
            "<td>" + "#" + "</td>"+ 
            "<td>" + "Arthritis" + "</td>"+ 
            "<td>" + "Asthma" + "</td>"+ 
            "<td>" + "Cancer" + "</td>"+ 
            "<td>" + "Dementia" + "</td>"
            + "<td>" + "Diabetes" + "</td>"
            + "<td>" + "Heart Disease" + "</td>"
            + "<td>" + "Kidney Disease" + "</td>"
            + "<td>" + "Lung Condition" + "</td>"
            + "<td>" + "Mental Health" + "</td>"
            + "<td>" + "Other" + "</td>"
            + "<td>" + "Stroke" + "</td>"
            + "</tr>";


            j = 1;

            for(int k = 0; k <table.size(); k += 11){
                Education medicals = table.get(k);

                html += "<tr><td>" + medicals.getColumn2() + "</td>";
                html = html + "<td>" + j + "</td>";

                for(int i = 0; i < 11; i++){
                    Education medicals2 = table.get(i+k);
                    html = html + "<td>" + medicals2.getColumn4() + "</td>";
                }
                html = html + "</tr>";
                j++;
            }

            break;


        
        case "NonSchoolCompletion":
            table = jdbc.getNonSchool(LGAcode, numSimilarLGAs,IndigenousStat,Gender,specificConstraint, year);
            html = html + "<td>LGA Names</td>" + 
            "<td>" + "#" + "</td>"+ 
            "<td>" + "Advanced Diploma and Diploma Level" + "</td>"+ 
            "<td>" + "Bachelor Degree Level" + "</td>"+ 
            "<td>" + "Certificate I & II Level" + "</td>"+ 
            "<td>" + "Certificate III & IV Level" + "</td>"+ 
            "<td>" + "Postgraduate Degree Level, Graduate Diploma and Graduate Certificate Level" + "</td>"
            + "</tr>";



            j = 1;

            for(int k = 0; k < table.size(); k += 5){
                Education Nschool = table.get(k);

                html += "<tr><td>" + Nschool.getColumn2() + "</td>";
                html = html + "<td>" + j + "</td>";

                for(int i = 0; i < 5; i++){
                    Education NonSc2 = table.get(i+k);
                    html = html + "<td>" + NonSc2.getColumn4() + "</td>";
                }
                html = html + "</tr>";
                j++;
            }


            break;



        
        case "AgeDemographics":
            html = html + "<td>LGA Names</td>" + 
            "<td>" + "#" + "</td>"+ 
            "<td>" + "0-4" + "</td>"
            +"<td>" + "5-9" + "</td>"
            +"<td>" + "10-14" + "</td>"
            +"<td>" + "15-19" + "</td>"
            +"<td>" + "20-24" + "</td>"
            +"<td>" + "25-29" + "</td>"
            +"<td>" + "30-34" + "</td>"
            +"<td>" + "35-39" + "</td>"
            +"<td>" + "40-44" + "</td>"
            +"<td>" + "45-49" + "</td>"
            +"<td>" + "50-54" + "</td>"
            +"<td>" + "55-59" + "</td>"
            +"<td>" + "60-64" + "</td>"
            +"<td>" + "65+" + "</td>"
            + "</tr>";
            break;



    }

    html += "</table>";


}



        // Close Content div
        html = html + "</div>";
        html = html + "</div>";

        // Footer
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
