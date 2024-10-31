package hamid.sougouma.human_resource.dto;

import hamid.sougouma.human_resource.entity.Role;
import hamid.sougouma.human_resource.entity.Skill;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EmployeDTO {

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

    private String phone;

    private String address;

    private String gender;

    private LocalDate birthday;

    private Role role;

    private Set<Skill> skills;


}
