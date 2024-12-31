package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Poste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;

    private String description;

    @OneToMany(mappedBy = "poste")
    private List<Employee> employees;

    @ManyToOne
    private Department department;
}
