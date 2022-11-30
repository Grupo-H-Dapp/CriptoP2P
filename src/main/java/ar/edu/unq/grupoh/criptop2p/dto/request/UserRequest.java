package ar.edu.unq.grupoh.criptop2p.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {
    private String name; //3-30
    private String lastname; //3-30
    private String email; //email format
    private String address; //3-30
    @NotNull
    private String password; //1 minuscula,1 mayuscula , 1 caracter especial , Min 6
    private String cvu; //22 digitos
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
