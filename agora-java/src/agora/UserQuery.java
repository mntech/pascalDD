package agora;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;

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
    
    @Column(name="query_string")
    public String getQuery() { return query; }
    
    public void setQuery(String query) {
        this.query = query;
    }
}
