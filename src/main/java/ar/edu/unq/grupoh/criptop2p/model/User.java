package ar.edu.unq.grupoh.criptop2p.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    public static final String passRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{6,}$";
    public static final String digitsRegex = "[0-9]+";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
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

    public User() {
    }

    public User(String name, String lastname, String email, String address, String password, String cvu, String addressWallet) {

        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.addressWallet = addressWallet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getAddressWallet() {
        return addressWallet;
    }

    public void setAddressWallet(String addressWallet) {
        this.addressWallet = addressWallet;
    }
}
