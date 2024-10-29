package hamid.sougouma.human_resource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Skill {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String level;
}
