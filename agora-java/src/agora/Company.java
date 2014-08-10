package agora;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="company")
public class Company implements java.io.Serializable {
    public int id;
    public String name;
    
    @Id
    @Column(name="comp_id")
    public int getId() { return id; }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="company_name")
    public String getName() { return name; }
    
    public void setName(String name) {
        this.name = name;
    }
}