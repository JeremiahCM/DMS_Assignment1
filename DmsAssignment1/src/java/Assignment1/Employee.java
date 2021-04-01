/*
 * JPA Entity class that represents an Employee
 * Entity has attribute for employee ID, first name, last name and job title
 * Implemented get and set methods to acccess and manage these attributes
 * @author Jeremiah Martinez: dgn1399
 */
package Assignment1;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Define class Entity to be used for dgn1399_employees table in database
@Entity
@Table(name = "dgn1399_employees")
public class Employee implements Serializable
{
    /*Entity will have an automatically generated ID, incremented one above the
    previous employee ID generated*/
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int e_id;
    @Column(name = "first_name")
    private String fName;
    @Column(name = "last_name")
    private String lName;
    @Column(name = "job")
    private String empJob;

    public Employee()
    {
    }

    //Setter for first name
    public void setFname(String fName)
    {
        this.fName = fName;
    }

    //Getter for first name
    public String getFname()
    {
        return fName;
    }

    //Setter for last name
    public void setLname(String lName)
    {
        this.lName = lName;
    }

    //Getter for last name
    public String getLname()
    {
        return lName;
    }

    //Setter for job title
    public void setEmpJob(String empJob)
    {
        this.empJob = empJob;
    }

    //Getter for job title
    public String getEmpJob()
    {
        return empJob;
    }

    //Setter for employee ID
    public void setEmpID(int e_id)
    {
        this.e_id = e_id;
    }

    //Getter for employee ID
    public int getEmpID()
    {
        return e_id;
    }
}
