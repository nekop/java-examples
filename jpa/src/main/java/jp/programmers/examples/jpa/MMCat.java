package jp.programmers.examples.jpa;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;

@Entity
public class MMCat {

    @Id @GeneratedValue
    private Integer id;
    private String name;
    @ManyToMany
    private Set<MMCat> friends = new HashSet<MMCat>();
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
}
