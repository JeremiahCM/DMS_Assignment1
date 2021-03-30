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
public class EmployeePK implements Serializable{
    
   private String fName;
   private String lName;
   private String employeeID;
   
   EmployeePK()
   {
      fName = null;
      lName = null;
      employeeID = null;
   }
   
   EmployeePK(String firstName, String lastName)
   {
       this.fName = firstName;
       this.lName = lastName;
   }
   
 /*  public String getFirstName()
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
   */
   
   @Override
   public boolean equals(Object obj)
   {
      if (obj == null || !(obj instanceof EmployeePK))
         return false;
      else
      {
         EmployeePK other = (EmployeePK) obj;
         if ((fName != null && fName.equals(other.fName))
            || (fName == null && other.fName == null))
         {
            if ((lName != null && lName.equals(other.lName))
               || (lName == null && lName.equals(other.lName)))
               return true;
            else
               return false;
         }
         else
            return false;
      }
   }

    @Override
   public int hashCode()
   {
      if (fName == null || lName == null)
         return 0;
      else
         return fName.hashCode() ^ lName.hashCode();
   }

    
    
}
