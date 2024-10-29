package hamid.sougouma.human_resource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Experience {

    @Id
    @GeneratedValue
    private long id;

    private String company;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
