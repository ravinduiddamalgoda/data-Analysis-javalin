package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vtp.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs2016() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2016'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("LGAcode");
                String name  = results.getString("name");

                // Create a LGA Object
                LGA lga = new LGA(code, name, 2016);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    public ArrayList<LGA> getLGAs2021() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA WHERE year='2021'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("LGAcode");
                String name  = results.getString("name");

                // Create a LGA Object
                LGA lga = new LGA(code, name, 2021);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    public ArrayList<Personas> Personas(String name, String Category) {
        // Create the ArrayList of LGA objects to return

        ArrayList<Personas> personas = new ArrayList<Personas>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Persona WHERE Name = '" + name + "' AND Category = '" + Category + "'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String Description  = results.getString("Description");

                // Create a LGA Object
                Personas persona = new Personas(name, Category, Description);

                // Add the lga object to the array
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the Personas
        return personas;
    }

    public ArrayList<Members> getMembers(){
        // Create the ArrayList of Members objects to return

        ArrayList<Members> members = new ArrayList<Members>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Members";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String Name  = results.getString("Name");
                String Snumber  = results.getString("Snumber");
                String Semail  = results.getString("Semail");

                // Create a LGA Object
                Members member = new Members(Name, Snumber, Semail);

                // Add the lga object to the array
                members.add(member);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the Personas
        return members;
    }


    public ArrayList<Education> EducationbyLGA(String LGAorStates, String SchoolorNonSchool, String year, String sortColumn, String Order, String population, String IndigStat, String Sex){
        // Create the ArrayList of Education objects to return

        ArrayList<Education> edu = new ArrayList<Education>();
        String LGstate = "";
        String schoolnschool = "";
        String syearNsrbracket = "";
        boolean notnumCol = true;

        if(sortColumn.equals("LGAcode") || sortColumn.equals("name") || sortColumn.equals("stateAbbr")){
            notnumCol = true;
        }
        else{notnumCol = false;
            if(SchoolorNonSchool.equals("School")){
                switch(sortColumn){
                    case "col2":
                        sortColumn = "0";
                        break;
                    case "col3":
                        sortColumn = "8";
                        break;
                    case "col4":
                        sortColumn = "9";
                        break;
                    case "col5":
                        sortColumn = "10";
                        break;
                    case "col6":
                        sortColumn = "11";
                        break;
                    case "col7":
                        sortColumn = "12";
                        break;
                }
            }
            else{
                switch(sortColumn){
                    case "col2":
                        sortColumn = "adip_dip";
                        break;
                    case "col3":
                        sortColumn = "bd";
                        break;
                    case "col4":
                        sortColumn = "ct_i_ii";
                        break;
                    case "col5":
                        sortColumn = "ct_iii_iv";
                        break;
                    case "col6":
                        sortColumn = "pd_gd_gc";
                        break;
                }
            }
        }

        if(LGAorStates.equals("LGAs")){
            LGstate = "sc.LGAcode";
        }
        else{
            LGstate = "LGA.StateAbbr";
        }


        if(SchoolorNonSchool.equals("School")){
            schoolnschool = "SchoolCompletion";
            syearNsrbracket = "SchoolYear";
        }
        else{
            schoolnschool = "NonSchoolCompletion";
            syearNsrbracket = "NonSchoolBracket";
        }

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "";
            // The Query
            int divide = 12;
            if(schoolnschool.equals("NonSchoolCompletion")){divide = 10;}

            if(!(sortColumn.equals("LGAcode") || sortColumn.equals("stateAbbr") || sortColumn.equals("LGAcode") || sortColumn.equals("name"))){
                if(Order.equals("ASC")){
                    Order = "DESC";
                }
                else{Order ="ASC";}
            }


            if(notnumCol){
                if(population.equals("true")){
                query = "select sc.LGAcode,lga.StateAbbr,lga.name,sc."+ syearNsrbracket + ", Round(sum(sc.Count),0) as count, sum(population) from " + schoolnschool + " as sc " +
                        "join LGA as lga on lga.LGAcode=sc.LGAcode and lga.year=sc.Year join LGApopulation as pop on sc.LGAcode = pop.LGAcode and sc.year = pop.Year\r\n" + //
                        "where sc.Year = "+ year + IndigStat + Sex + "\r\n" + //
                        "\r\n" + //
                        "group by sc." + syearNsrbracket + ", " + LGstate + "\r\n" + //
                        "order by LGA." + sortColumn + " " + Order + "\r\n" + //
                        ";";
                }
                else{
                query = "select sc.LGAcode,lga.StateAbbr,lga.name,sc2."+ syearNsrbracket + ", Round((sum(sc.Count)/(population) * 100),2) as count, sum(population) from " + schoolnschool + " as sc " +
                        "join LGA as lga on lga.LGAcode=sc.LGAcode and lga.year=sc.Year join LGApopulation as pop on sc.LGAcode = pop.LGAcode and sc.year = pop.Year\r\n" + //
                        "where sc.Year = "+ year + IndigStat + Sex + "\r\n" + //
                        "\r\n" + //
                        "group by sc." + syearNsrbracket + ", " + LGstate + "\r\n" + //
                        "order by LGA." + sortColumn + " " + Order + "\r\n" + //
                        ";"
                        ;
                }
            }
            else{
                if(population.equals("true")){
                query = "select sc.LGAcode, name, stateabbr, round(sum(sc2.count/" + divide + "),0) as ordering, sc." + syearNsrbracket + ",  round(sum(sc.count/" + divide + "),0) as count from " + schoolnschool + " as sc join LGA as lga on lga.lgacode=sc.lgacode and lga.year=sc.Year " + 
                    "join LGApopulation as pop on sc.LGAcode = pop.LGAcode and sc.year = pop.Year " +
                    "join " + schoolnschool + " as sc2 on sc.lgacode = sc2.lgacode " + 
                    "where sc.Year = "+ year + IndigStat + Sex + " and sc2." + syearNsrbracket + " = '" + sortColumn +  "'\r\n" + //
                    "group by " + LGstate + ", sc." + syearNsrbracket +  "\r\n" + //
                    "order by ordering "+ Order;
                }
                else{
                query = "select sc.LGAcode, name, stateabbr,round(sum(sc2.count/" + divide + "),0) as ordering, sc." + syearNsrbracket + ", round(round(sum(sc.count/"+ divide + "),2)/population *100 ,2) as count from " + schoolnschool + " as sc join LGA as lga on lga.lgacode=sc.lgacode and lga.year=sc.Year " + 
                    "join LGApopulation as pop on sc.LGAcode = pop.LGAcode and sc.year = pop.Year " +
                    "join " + schoolnschool + " as sc2 on sc.lgacode = sc2.lgacode " + 
                    "where sc.Year = "+ year + IndigStat + Sex + " and sc2." + syearNsrbracket + " = '" + sortColumn +  "'\r\n" + //
                    "group by " + LGstate + ", sc." + syearNsrbracket +  "\r\n" + //
                    "order by ordering " + Order;
                }

            }


            // Get Result
            ResultSet results = statement.executeQuery(query);  

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String Column1  = results.getString("LGAcode");
                String Column2  = results.getString("StateAbbr");
                String Column3  = results.getString("name");
                String Column4  = results.getString(syearNsrbracket);
                String Column5  = results.getString("count");


                if (Column5 == null){Column5 = "0";}
                

                if(population.equals("true")){
                   Column5 = Column5.replace(".0", "");
                }
                


                // Create a LGA Object
                Education education = new Education(Column1, Column2, Column3, Column4, Column5);

                // Add the lga object to the array
                edu.add(education);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the Personas
        return edu;
    }



// get Similar LGAs
// Get Medical Condition LGA

public ArrayList<Education> getMedical(String LGAcode, String numSimilarLGAs, String IndigenousStat, String Gender, String condition){
        // Setup the variable for the JDBC connection
        Connection connection = null;
        ArrayList<Education> table = new ArrayList<Education>();
        String original = "";
       
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String query;
            if(!(condition.equals("All"))){
                query = "select sc.LGAcode, sc.condition, sc.Indigenous_Status, sc.Sex, sc.count from MedicalConditions as sc "
                + "where sc.year = 2021 and sc.LGAcode = '" + LGAcode + "' ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + "' ";
                }
                
                query += " and sc.condition = '" + condition + "' ";
                
                query += "group by sc.LGAcode, sc.condition";
            }
            else{
                query = "select sc.LGAcode, sc.condition, sc.Indigenous_Status, sc.Sex, sum(sc.count), sc.count from MedicalConditions as sc "
                + "where sc.year = 2021 and sc.LGAcode = '" + LGAcode + "' ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + "' ";
                }
                
                query += "group by sc.LGAcode";
            }
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the column
                original = results.getString(5);
            }


        }catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        connection = null;


        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query;
            
            if(!(condition.equals("All"))){
                query = "select sc.LGAcode, lga.Name, sc.condition, sc.Indigenous_Status, sc.Sex, sc.count , sc2.condition, sc2.count from MedicalConditions as sc "
                + "join LGA as lga on lga.LGAcode = sc.LGAcode and lga.year = sc.year " 
                + "join MedicalConditions as sc2 on sc2.LGAcode = sc.LGAcode "
                + "where sc.year = 2021 and sc2.year = 2021 ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + 
                    "'" + " and sc2.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + 
                    "'" + " and sc2.Sex = '" + Gender + "' ";
                }
                
                query += " and sc.condition = '" + condition + "' " + 
                "and sc2.condition = '" + condition + "' ";
            
                query += " group by sc.LGAcode, sc.condition " + 
                " order by ABS((sc2.count)-" + original + ") limit 11*" + numSimilarLGAs;
            }
            else{
                query = "select sc.LGAcode, lga.Name, sc.condition, sc.Indigenous_Status, sc.Sex, sc.count, sc2.condition, sc2.count, sum(sc2.count) from MedicalConditions as sc "
                + "join LGA as lga on lga.LGAcode = sc.LGAcode and lga.year = sc.year " 
                + "join MedicalConditions as sc2 on sc2.LGAcode = sc.LGAcode "
                + "where sc.year = 2021 and sc2.year = 2021 ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + 
                    "'" + " and sc2.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + 
                    "'" + " and sc2.Sex = '" + Gender + "' ";
                }
            
                query += "group by sc.LGAcode, sc.condition " + 
                " order by ABS(sum(sc2.count)-" + original + ") limit 11*" + numSimilarLGAs;
            }
            

            
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String Column1 = results.getString(1);
                String Column2 = results.getString(2);
                String Column3 = results.getString(3);
                String Column4 = results.getString(6);
                String Column5 = results.getString(8);


                Education Recent = new Education(Column1, Column2, Column3, Column4, Column5);
                table.add(Recent);
            }


        }catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return table;
    }

public ArrayList<Education> getNonSchool(String LGAcode, String numSimilarLGAs, String IndigenousStat, String Gender, String bracket, String year){
        // Setup the variable for the JDBC connection
        Connection connection = null;
        ArrayList<Education> table = new ArrayList<Education>();
        String original = "";
       
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String query;
            if(!(bracket.equals("All"))){
                query = "select sc.LGAcode, sc.NonSchoolBracket, sc.Indigenous_Status, sc.Sex, sc.count from NonSchoolCompletion as sc "
                + "where sc.year = "+ year + " and sc.LGAcode = '" + LGAcode + "' ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + "' ";
                }
                
                query += " and sc.NonSchoolBracket = '" + bracket + "' ";
                
                query += "group by sc.LGAcode, sc.NonSchoolBracket";
            }
            else{
                query = "select sc.LGAcode, sc.NonSchoolBracket, sc.Indigenous_Status, sc.Sex, sum(sc.count), sc.count from NonSchoolCompletion as sc "
                + "where sc.year = "+ year + " and sc.LGAcode = '" + LGAcode + "' ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + "' ";
                }
                
                query += "group by sc.LGAcode";
            }
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the column
                original = results.getString(5);
            }


        }catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        connection = null;


        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query;
            
            if(!(bracket.equals("All"))){
                query = "select sc.LGAcode, lga.Name, sc.NonSchoolBracket, sc.Indigenous_Status, sc.Sex, sc.count , sc2.NonSchoolBracket, sc2.count from NonSchoolCompletion as sc "
                + "join LGA as lga on lga.LGAcode = sc.LGAcode and lga.year = sc.year " 
                + "join NonSchoolCompletion as sc2 on sc2.LGAcode = sc.LGAcode "
                + "and sc.year = "+ year + " and sc2.year = " + year + " ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + 
                    "'" + " and sc2.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + 
                    "'" + " and sc2.Sex = '" + Gender + "' ";
                }
                
                query += " and sc.NonSchoolBracket = '" + bracket + "' " + 
                "and sc2.NonSchoolBracket = '" + bracket + "' ";
            
                query += " group by sc.LGAcode, sc.NonSchoolBracket " + 
                " order by ABS((sc2.count)-" + original + ") limit 11*" + numSimilarLGAs;
            }
            else{
                query = "select sc.LGAcode, lga.Name, sc.NonSchoolBracket, sc.Indigenous_Status, sc.Sex, sc.count, sc2.NonSchoolBracket, sc2.count, sum(sc2.count) from NonSchoolCompletion as sc "
                + "join LGA as lga on lga.LGAcode = sc.LGAcode and lga.year = sc.year " 
                + "join NonSchoolCompletion as sc2 on sc2.LGAcode = sc.LGAcode "
                + "and sc.year = "+ year + " and sc2.year = " + year + " ";

                if(!(IndigenousStat.equals("All"))){
                    query += " and sc.Indigenous_Status = '" + IndigenousStat + 
                    "'" + " and sc2.Indigenous_Status = '" + IndigenousStat + "' ";
                }
                if(!(Gender.equals("All"))){
                    query += " and sc.Sex = '" + Gender + 
                    "'" + " and sc2.Sex = '" + Gender + "' ";
                }
            
                query += "group by sc.LGAcode, sc.NonSchoolBracket " + 
                " order by ABS(sum(sc2.count)-" + original + ") limit 5*" + numSimilarLGAs;
            }
            

            
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String Column1 = results.getString(1);
                String Column2 = results.getString(2);
                String Column3 = results.getString(3);
                String Column4 = results.getString(6);
                String Column5 = results.getString(8);

                
                Column4 = Column4.replace(".0", "");


                Education Recent = new Education(Column1, Column2, Column3, Column4, Column5);
                table.add(Recent);
            }


        }catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return table;
    }

public ArrayList<Education> getSchool(String LGAcode, String numSimilarLGAs, String IndigenousStat, String Gender, String Minyear, String Maxyear, String year){
        // Setup the variable for the JDBC connection
        Connection connection = null;
        ArrayList<Education> table = new ArrayList<Education>();
        String original = "";
       
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String query;
            query = "";
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the column
                original = results.getString(5);
            }


        }catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        connection = null;


        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query;
            
            
            query = "";
            

            
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                // Lookup the columns we need
                String Column1 = results.getString(1);
                String Column2 = results.getString(2);
                String Column3 = results.getString(3);
                String Column4 = results.getString(6);
                String Column5 = results.getString(8);

                
                Column4 = Column4.replace(".0", "");


                Education Recent = new Education(Column1, Column2, Column3, Column4, Column5);
                table.add(Recent);
            }


        }catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return table;
    }

    public ArrayList<Map<String, Object>> retrieveCombinedData() {
        ArrayList<Map<String, Object>> combinedDataList = new ArrayList<Map<String, Object>>();
    
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            String query = "SELECT ad.LGAcode, ad.year, ad.Indigenous_Status, ad.Sex, ad.AgeRange, ad.count AS ageCount, mc.Condition, mc.count AS conditionCount " +
                           "FROM AgeDemographics ad " +   
                           "JOIN MedicalConditions mc ON ad.LGAcode = mc.LGAcode AND ad.year = mc.year LIMIT 100";
    
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Map<String, Object> rowData = new HashMap<>();
                rowData.put("LGAcode", results.getInt("LGAcode"));
                rowData.put("year", results.getString("year"));
                rowData.put("Indigenous_Status", results.getString("Indigenous_Status"));
                rowData.put("Sex", results.getString("Sex"));
                rowData.put("AgeRange", results.getString("AgeRange"));
                rowData.put("ageCount", results.getInt("ageCount"));
                rowData.put("Condition", results.getString("Condition"));
                rowData.put("conditionCount", results.getInt("conditionCount"));
                combinedDataList.add(rowData);
            }
    
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    
        return combinedDataList; 
    }

    public ArrayList<Education> getFilteredData(String ageRangeFilter, String genderFilter) {
        Connection connection = null;
        ArrayList<Education> table = new ArrayList<Education>();
        // char gender;
        String gender = "";
        if(genderFilter.toUpperCase().equals("MALE")){
            gender = "M";
        }else if(genderFilter.toUpperCase().equals("FEMALE")){
             gender = "F";
        }
        try {
            // Connect to the JDBC database
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // Customize the query based on the provided age and gender filters
            String query = "SELECT ad.LGAcode, ad.year, ad.Indigenous_Status, ad.Sex, ad.AgeRange, ad.count as ageCount, mc.Condition, mc.count as conditionCount " +
                           "FROM AgeDemographics ad " +
                           "JOIN MedicalConditions mc ON ad.LGAcode = mc.LGAcode AND ad.year = mc.year " +
                           "WHERE ad.AgeRange IN ('"+ageRangeFilter+"') AND ad.Sex = '"+gender +"' LIMIT 100";
    
            ResultSet results = statement.executeQuery(query);
            if(results.next()){
                System.out.println("Data Found");

            }else {
                System.out.println("No Data Found");
            }

            while (results.next()) {
                String Column1 = results.getString(1);
                String Column2 = results.getString(2);
                String Column3 = results.getString(3);
                String Column4 = results.getString(6);
                String Column5 = results.getString(8);
                
                Column4 = Column4.replace(".0", "");
                
                Education Recent = new Education(Column1, Column2, Column3, Column4, Column5);
                table.add(Recent);
            }
            System.out.println(table.size());
    
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("Error Occured HEre ");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        
        return table;
    }
    
    
}


