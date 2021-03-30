/*
 * JPA Entity class that represents a Pet
 * @see PetEntityServlet.java
 */
package Assignment1;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "dgn1399_employees")
@IdClass(value = EmployeePK.class)
public class Employee implements Serializable
{
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Column(name = "first_name")
   private String fName;
   @Column(name = "last_name")
   private String lName;
   @Column(name = "job")
   private String empJob;

   public Employee()
   {
   }
   
   
   public void setFname(String fName)
   {
      this.fName = fName;
   }

   public String getFname()
   {
      return fName;
   }

   public void setLname(String lName)
   {
      this.lName = lName;
   }

   public String getlName()
   {
      return lName;
   }

   public void setEmpJob(String empJob)
   {
      this.empJob = empJob;
   }

   public String getEmpJob()
   {
      return empJob;
   }



}
