package md.felicia.symphomybufferprep.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AuthenticationDTO {
    @NotEmpty(message = "The login must be not empty")
    @Size(min = 2, max = 30, message = "login length must be between 2 and 30 char")
    private String login;

    @NotEmpty(message = "The password is required")
    @Size(min = 8, max = 30, message = "password length must be between 2 and 30 char")
    private String password;
}
