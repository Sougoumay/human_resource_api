package hamid.sougouma.human_resource.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hamid.sougouma.human_resource.entity.Role;
import hamid.sougouma.human_resource.entity.Skill;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EmployeDTO {

    private long id;

    @JsonView(Views.Resume.class)
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    private String firstName;

    @JsonView(Views.Resume.class)
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 15)
    private String lastName;

    @JsonView(Views.Resume.class)
    @NotNull
    @Email
    private String email;

    @JsonView(Views.Resume.class)
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @JsonView(Views.Resume.class)
    private String phone;

    @JsonView(Views.Resume.class)
    private String address;

    @JsonView(Views.Resume.class)
    private String gender;

    @JsonView(Views.Resume.class)
    private LocalDate birthday;

    @JsonView(Views.Resume.class)
    private Role role;

    @JsonView(Views.Complet.class)
    private Set<Skill> skills;




}
