package hamid.sougouma.human_resource.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ExperienceDTO {

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
    @DateTimeFormat
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat
    private LocalDate endDate;
}
