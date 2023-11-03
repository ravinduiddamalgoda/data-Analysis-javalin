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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
                <a href='page2B.html'>Education</a>
                <a href='page3A.html'>Sub Task 3.A</a>
                <a href='page3B.html'>Find SImilar LGAs</a>
            </div>
        """;
        // This example uses JDBC to lookup the LGAs
        JDBCConnection jdbc = new JDBCConnection();

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Our Mission, Team and Information</h1>
            </div>
        """;


/*
    Mission Statement
 */
        html = html + "<div class='MissionState'>";
        html = html + "<h1>The Mission Goal</h1>";
        
        html = html + "<p>" + MissionStatement + 
        "</p>";
        



        html = html + "</div>";




/*
    Persona's and Group Memebers
 */

        // Add Div for page Content
        html = html + "<div class='PersonasMem'>";
        String PersonaName1 =  "Pupil Kiara";
        String PersonaName2 =  "Procrastinating Palmer";
        String PersonaName3 =  "Data-Driven Dana";

        // Add Div for Members
        html = html + "<div class='Members'>";

        // Next we will ask this *class* for the LGAs
        ArrayList<Members> Members = jdbc.getMembers();

        // Add HTML for the LGA list
        html = html + "<h1>All Developers of this Site</h1>" + "<ul>";

        // Finally we can print out all of the LGAs
        for (Members member : Members) {
            html = html + "<li>" + member.getSname()
                        + " - " + member.getSnumber()
                        + " - " + member.getSemail() + "</li>";
        }

        html = html + "</ul></div>";

        
        // Add Div for page Kiara
        html = html + "<div class='PupilKiara'>";
        

        // Add HTML for the Persona list
        html = html + "<h1>The Persona " + PersonaName1 + "</h1>" + "<ul>";
        ArrayList<Personas> Perona1img = jdbc.Personas(PersonaName1, "ImageLocation");
        for (Personas Personas : Perona1img) {
            html += "<img src = '" + Personas.getDescription() + "' id = '"+ PersonaName1 + "'";
        }

        // Ask Persona for persona list by attributes
        ArrayList<Personas> Perona1Attris = jdbc.Personas(PersonaName1, "Attributes");
        html = html + "<p>Attributes</p>";

        // First Print Attributes
        for (Personas Personas : Perona1Attris) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Goals</p>";
 
        // Ask Persona for persona list by Goals
        ArrayList<Personas> Perona1Goals = jdbc.Personas(PersonaName1, "Goals");

        // Second Print Goals
        for (Personas Personas : Perona1Goals) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Needs</p>";

         // Ask Persona for persona list by Needs
        ArrayList<Personas> Perona1Needs = jdbc.Personas(PersonaName1, "Needs");

        // Third Print Needs
        for (Personas Personas : Perona1Needs) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Skills and Experience</p>";

         // Ask Persona for persona list by Skills and Experience
        ArrayList<Personas> Perona1Exp = jdbc.Personas(PersonaName1, "Skills and Experience");

        // last Print Skills and Experience
        for (Personas Personas : Perona1Exp) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }

        html = html + "</ul></div>";


        // Add Div for page Palmer
        html = html + "<div class='ProcrastinatingPalmer'>";
        

        // Add HTML for the Persona list
        html = html + "<h1>The Persona " + PersonaName2 + "</h1>" + "<ul>";
        ArrayList<Personas> Perona2img = jdbc.Personas(PersonaName2, "ImageLocation");
        for (Personas Personas : Perona2img) {
            html += "<img src = '" + Personas.getDescription() + "' id = '"+ PersonaName2 + "'";
        }

        // Ask Persona for persona list by attributes
        ArrayList<Personas> Perona2Attris = jdbc.Personas(PersonaName2, "Attributes");
        html = html + "<p>Attributes</p>";

        // First Print Attributes
        for (Personas Personas : Perona2Attris) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Goals</p>";
 
        // Ask Persona for persona list by Goals
        ArrayList<Personas> Perona2Goals = jdbc.Personas(PersonaName2, "Goals");

        // Second Print Goals
        for (Personas Personas : Perona2Goals) {
            html = html + "<li>" +  Personas.getDescription() + "</li>";
        }
        html = html + "<p>Needs</p>";

         // Ask Persona for persona list by Needs
        ArrayList<Personas> Perona2Needs = jdbc.Personas(PersonaName2, "Needs");

        // Third Print Needs
        for (Personas Personas : Perona2Needs) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Skills and Experience</p>";

         // Ask Persona for persona list by Skills and Experience
        ArrayList<Personas> Perona2Exp = jdbc.Personas(PersonaName2, "Skills and Experience");

        // last Print Skills and Experience
        for (Personas Personas : Perona2Exp) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }

        html = html + "</ul></div>";


        // Add Div for page Dana
        html = html + "<div class='DataDrivenDana'>";
        

        // Add HTML for the Persona list
        html = html + "<h1>The Persona " + PersonaName3 + "</h1>" + "<ul>";
        ArrayList<Personas> Perona3img = jdbc.Personas(PersonaName3, "ImageLocation");
        for (Personas Personas : Perona3img) {
            html += "<img src = '" + Personas.getDescription() + "' id = '"+ PersonaName3 + "'";
        }
        

        // Ask Persona for persona list by attributes
        ArrayList<Personas> Perona3Attris = jdbc.Personas(PersonaName3, "Attributes");
        html = html + "<p>Attributes</p>";

        // First Print Attributes
        for (Personas Personas : Perona3Attris) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Goals</p>";
 
        // Ask Persona for persona list by Goals
        ArrayList<Personas> Perona3Goals = jdbc.Personas(PersonaName3, "Goals");

        // Second Print Goals
        for (Personas Personas : Perona3Goals) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Needs</p>";

         // Ask Persona for persona list by Needs
        ArrayList<Personas> Perona3Needs = jdbc.Personas(PersonaName3, "Needs");

        // Third Print Needs
        for (Personas Personas : Perona3Needs) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }
        html = html + "<p>Skills and Experience</p>";

         // Ask Persona for persona list by Skills and Experience
        ArrayList<Personas> Perona3Exp = jdbc.Personas(PersonaName3, "Skills and Experience");

        // last Print Skills and Experience
        for (Personas Personas : Perona3Exp) {
            html = html + "<li>" + Personas.getDescription() + "</li>";
        }

        // Finish the List HTML
        html = html + "</ul></div></div>";

 



/*
    LGAs
 */

        // Add Div for page Content
        html = html + "<div class='LGA'>";

        // Next we will ask this *class* for the LGAs
        ArrayList<LGA> lgas16 = jdbc.getLGAs2016();
        
        // Add Div for page LGA2016
        html = html + "<div class='LGA2016'>";

        // Add HTML for the LGA list
        html = html + "<h1>All 2016 LGAs in the Voice to Parliament database</h1>" + "<ul>";

        // Finally we can print out all of the LGAs
        for (LGA lga : lgas16) {
            html = html + "<li>" + lga.getCode()
                        + " - " + lga.getName() + "</li>";
        }

        // Finish the List HTML
        html = html + "</ul></div>";

        
        // Add Div for page LGA2016
        html = html + "<div class='LGA2021'>";
        // Next we will ask this *class* for the LGAs
        ArrayList<LGA> lgas21 = jdbc.getLGAs2021();

        // Add HTML for the LGA list
        html = html + "<h1>All 2021 LGAs in the Voice to Parliament database</h1>" + "<ul>";

        // Finally we can print out all of the LGAs
        for (LGA lga : lgas21) {
            html = html + "<li>" + lga.getCode()
                        + " - " + lga.getName() + "</li>";
        }

        // Finish the List HTML
        html = html + "</ul></div>";
        


        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
            <div class='footer'>
                <p>The Mission of This Site</p>
            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }



private String MissionStatement = "How It Addresses Social Challenge" + "<p></p>" +
"Describe how it works"; 

}