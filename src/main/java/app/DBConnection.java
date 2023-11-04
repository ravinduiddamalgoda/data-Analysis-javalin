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


public class DBConnection {
    
    public static final String DATABASE = "jdbc:sqlite:database/project_database.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public DBConnection() {
        System.out.println("Created JDBC Connection Object");
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


    public ArrayList<Data2A> FilteredData(String ageGroupFilter, String healthConditionFilter, String genderFilter, String sortField , String yearData , String indigenous_status) {
        Connection connection = null;
        ArrayList<Data2A> table = new ArrayList<Data2A>();
    
        try {
            // Connect to the JDBC database
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            System.out.println(ageGroupFilter);
            System.out.println(healthConditionFilter);
            System.out.println(genderFilter);
            System.out.println(sortField);
            System.out.println(yearData);
            System.out.println(indigenous_status);
            // Customize the query based on the provided filters and sort field
            String query = "SELECT a.year, a.age_group, h.health_condition, a.indigenous_status, " +
                           "SUM(h.count) AS total_health_condition_count, SUM(a.count) AS total_age_demographics_count, a.sex " +
                           "FROM AgeDemographics a " +
                           "INNER JOIN HealthConditions h ON a.lga_code = h.lga_code " +
                           "WHERE h.health_condition = '" + healthConditionFilter + "' " +
                           "AND a.age_group = '" + ageGroupFilter + "' " +
                           "AND a.sex = '" + genderFilter + "' " +
                           "AND a.year = '"+ yearData + "'" +
                           "AND a.indigenous_status  = '"+ indigenous_status + "'" +
                           "GROUP BY a.year, a.age_group, h.health_condition, a.indigenous_status, a.sex ";
    
            if (sortField != null && !sortField.isEmpty()) {
                query += "ORDER BY a." + sortField;
                // query += "ORDER BY a.year";
            } else {
                query += "ORDER BY a.year";
            }
    
            ResultSet results = statement.executeQuery(query);
    
            while (results.next()) {
                String year = results.getString("year");
                String ageGroup = results.getString("age_group");
                String healthCondition = results.getString("health_condition");
                String indigenousStatus = results.getString("indigenous_status");
                int totalHealthConditionCount = results.getInt("total_health_condition_count");
                int totalAgeDemographicsCount = results.getInt("total_age_demographics_count");
                String sex = results.getString("sex");
    
                Data2A recent = new Data2A(year, ageGroup, healthCondition, indigenousStatus, totalHealthConditionCount, totalAgeDemographicsCount, sex);
                table.add(recent);
            }
    
            System.out.println("Data retrieved successfully.");
            System.out.println(table.size());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("Error occurred.");
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
