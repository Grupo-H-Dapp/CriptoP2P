package ar.edu.unq.grupoh.criptop2p.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    public static final String passRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";
    public static final String digitsRegex = "[0-9]+";

    @NotBlank(message = "Name cannot be null")
    @Size(min = 3,max = 30)
    private String name; //3-30
    @NotBlank(message = "LastName cannot be null")
    @Size(min = 3,max = 30)
    private String lastname; //3-30
    @Email(message = "Email should be a valid email")
    private String email; //email format
    @NotBlank(message = "Address cannot be null")
    @Size(min = 3,max = 30)
    private String address; //3-30
    @Pattern(regexp = passRegex ,  message = "The password field should have at least be 6 length and have a lowecase, a uppercase, a special character")
    private String password; //1 minuscula,1 mayuscula , 1 caracter especial , Min 6
    @NotBlank(message = "Cvu cannot be null")
    @Size(min = 22,max = 22 , message = "The cvu field should have 22 length")
    @Pattern(regexp = digitsRegex, message = "The cvu field should only have digits")
    private String cvu; //22 digitos
    @NotBlank(message = "WalletAddress cannot be null")
    @Size(min = 8,max = 8, message = "The address wallet field should have 8 length")
    @Pattern(regexp = digitsRegex, message = "The walletAddress field should only have digits")
    private String addressWallet ; //8 digitos

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User() {
    }

    public User(String name, String lastname, String email, String address, String password, String cvu, String addressWallet, Long id) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.addressWallet = addressWallet;
        this.id = id;
    }
}
