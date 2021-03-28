/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment1;

import java.io.Serializable;
/**
 *
 * @author churr
 */
public class Employee implements Serializable{
    
   private String firstName;
   private String lastName;
   private String employeeID;
   
   Employee()
   {
      firstName = null;
      lastName = null;
      employeeID = null;
   }
   
   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }
   
   public void setEmployeeID(String id)
   {
       this.employeeID = id;
   }
   
   public String getEmployeeID()
   {
       return this.employeeID;
   }    
    

    
    
}
