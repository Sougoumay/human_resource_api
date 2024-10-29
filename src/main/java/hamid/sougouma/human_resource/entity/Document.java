package hamid.sougouma.human_resource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Document {

    @Id
    @GeneratedValue
    private long id;

    private String title;

}
