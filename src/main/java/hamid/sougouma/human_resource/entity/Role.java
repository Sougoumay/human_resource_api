package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {

    @Id @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;

//    @OneToMany(mappedBy = "role")
//    public List<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
