package hamid.sougouma.human_resource.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Employee employee;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private Role role;
}
