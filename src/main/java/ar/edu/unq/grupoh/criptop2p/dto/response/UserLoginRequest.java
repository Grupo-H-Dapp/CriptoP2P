package ar.edu.unq.grupoh.criptop2p.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    public static final String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";

    @NotNull(message = "email cannot be null")
    @Email(message = "email should be a valid email")
    @Getter @Setter
    private String email;

    @NotNull(message = "password cannot be null")
    @Pattern(regexp = passwordRegex, message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    @Getter @Setter
    private String password;
}
