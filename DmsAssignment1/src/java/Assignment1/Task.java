/*
 * JPA Entity class that represents a Task
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

//Define class Task to be used for dgn1399_etasks table in database
@Entity
@Table(name = "dgn1399_tasks")
public class Task implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int t_id;
    @Column(name = "task_name")
    private String taskName;
    
    public Task()
    {
    }
    
    public void setTask(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getTask()
    {
        return this.taskName;
    }
    
    public void setTaskID(int t_id)
    {
        this.t_id = t_id;
    }
    
    public int getTaskID()
    {
        return this.t_id;
    }
}
