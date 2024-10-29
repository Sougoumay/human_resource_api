package hamid.sougouma.human_resource.dto;

import hamid.sougouma.human_resource.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserDTO {

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

    private String password;

    private String phone;

    private String address;

    private String gender;

    private LocalDate birthday;

    private Role role;

}
