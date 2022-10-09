package ar.edu.unq.grupoh.criptop2p.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    @Column(unique=true)
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

    public static UserRequestBuilder builder() {
        return new UserRequestBuilder();
    }

    public static final class UserRequestBuilder {
        private UserRequest userRequest;

        private UserRequestBuilder() {
            userRequest = new UserRequest();
        }

        public UserRequestBuilder name(String name) {
            userRequest.setName(name);
            return this;
        }

        public UserRequestBuilder surname(String lastName) {
            userRequest.setLastname(lastName);
            return this;
        }

        public UserRequestBuilder email(String email) {
            userRequest.setEmail(email);
            return this;
        }

        public UserRequestBuilder address(String address) {
            userRequest.setAddress(address);
            return this;
        }

        public UserRequestBuilder password(String password) {
            userRequest.setPassword(password);
            return this;
        }

        public UserRequestBuilder cvu(String cvu) {
            userRequest.setCvu(cvu);
            return this;
        }

        public UserRequestBuilder walletAddress(String walletAddress) {
            userRequest.setAddressWallet(walletAddress);
            return this;
        }

        public UserRequest build() {
            return userRequest;
        }
    }
}
