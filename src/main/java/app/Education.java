package app;

/**
 * Class represeting a Persona from the Studio Project database
 * Copy and edited from LGA.java template, 
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */

public class Education {
   // Create Relevant Fields for school
   private String Column1;
   private String Column2;
   private String Column3;
   private String Column4;
   private String Column5;


   // constructor for Education
   public Education(String Column1, String Column2, String Column3, String Column4, String Column5) {
      this.Column1 = Column1;
      this.Column2 = Column2;
      this.Column3 = Column3;
      this.Column4 = Column4;
      this.Column5 = Column5;
   }

   // Create Relevant get methods for school

   public String getColumn1() {
      return Column1;
   }

   public String getColumn2() {
      return Column2;
   }

   public String getColumn3() {
      return Column3;
   }

   public String getColumn4() {
      return Column4;
   }

   public String getColumn5() {
      return Column5;
   }
}
