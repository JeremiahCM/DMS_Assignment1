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
public class Tasks implements Serializable{
    
    private String task;
    private int employeeID;
    
    Tasks()
    {
        task = null;
        employeeID = 0;
    }
    
    public void setTask(String task)
    {
        this.task = task;
    }
    
    public String getTask()
    {
        return this.task;
    }
    
    
    public void setEmployeeID(int id)
    {
        this.employeeID = id;
    }
    
    public int getEmployeeID()
    {
        return this.employeeID;
    }
    
    
    
}
