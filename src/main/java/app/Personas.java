package app;

/**
 * Class represeting a Persona from the Studio Project database
 * Copy and edited from LGA.java template, 
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 */

public class Personas {
   // Persona Description
   private String Description;

   // Persona Name
   private String Name;

   // Persona Category
   private String Category;

   /**
    * Create a Persona and set the fields
    */
   public Personas(String Name, String Category, String Description) {
      this.Description = Description;
      this.Name = Name;
      this.Category = Category;
   }

   public String getDescription() {
      return Description;
   }

   public String getName() {
      return Name;
   }

   public String getCategory() {
      return Category;
   }
}
