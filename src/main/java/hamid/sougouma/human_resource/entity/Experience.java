package hamid.sougouma.human_resource.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Experience {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    private String company;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    private String title;

    @Size(min = 2, max = 50)
    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @ManyToOne
    private Employee employee;
}
