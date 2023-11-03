package app;

/**
 * Class represeting a Persona from the Studio Project database
 * Copy and edited from LGA.java template, 
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */

public class Members {
   // Student Name
   private String Sname;

   // Stud Number
   private String Snumber;

   // Student Email
   private String Semail;

   
   public Members(String Sname, String Snumber, String Semail) {
      this.Semail = Semail;
      this.Sname = Sname;
      this.Snumber = Snumber;
   }

   public String getSnumber() {
      return Snumber;
   }

   public String getSname() {
      return Sname;
   }

   public String getSemail() {
      return Semail;
   }

}
