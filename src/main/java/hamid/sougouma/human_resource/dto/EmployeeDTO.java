package hamid.sougouma.human_resource.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class EmployeeDTO {

    private long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    private String firstName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    private boolean active;

    private String phone;

    private String address;

    private String gender;

    private String birthday;

    private String hireDate;

    private String role;

//    private Set<SkillDTO> skills;
//
//    private PosteDto poste;
//
//    private List<ExperienceDTO> experiences;






}
