package agora;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_query")
public class UserQuery implements java.io.Serializable {
    public int id;
    public String query;

    @Id
    @GeneratedValue(strategy=IDENTITY)
    public int getId() { return id; }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getQuery() { return query; }
    
    public void setQuery(String query) {
        this.query = query;
    }
}
