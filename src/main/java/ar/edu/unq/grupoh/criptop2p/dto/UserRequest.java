package ar.edu.unq.grupoh.criptop2p.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "Name cannot be null")
    @Size(min = 3,max = 30 , message = "Name must be between 3 and 30 characters long")
    private String name; //3-30
    @NotBlank(message = "LastName cannot be null")
    @Size(min = 3,max = 30 , message = "Lastname must be between 3 and 30 characters long")
    private String lastname; //3-30
    @Email(message = "Email should be a valid email")
    private String email; //email format
    @NotBlank(message = "Address cannot be null")
    @Size(min = 3,max = 30 , message = "Address must be between 3 and 30 characters long")
    private String address; //3-30
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$" ,  message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    private String password; //1 minuscula,1 mayuscula , 1 caracter especial , Min 6
    @NotBlank(message = "Cvu cannot be null")
    @Size(min = 22,max = 22 , message = "CVU must be between 22 digits long")
    @Pattern(regexp = "[0-9]+", message = "The cvu field should only have digits")
    private String cvu; //22 digitos
    @NotBlank(message = "WalletAddress cannot be null")
    @Size(min = 8,max = 8 , message = "Wallet must be between 8 digits long")
    @Pattern(regexp = "[0-9]+", message = "The walletAddress field should only have digits")
    private String addressWallet ; //8 digitos
}
