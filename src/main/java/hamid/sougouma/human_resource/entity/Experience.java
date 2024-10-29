package hamid.sougouma.human_resource.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Experience {

    @Id
    @GeneratedValue
    private long id;

    private String company;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    private Employee employee;
}
