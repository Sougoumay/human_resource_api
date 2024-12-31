package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String description;

    private String responsable;

    private double budget;

    private int nombreEmployes;

    @OneToMany
    private List<Poste> postes;

}
