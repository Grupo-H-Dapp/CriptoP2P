package ar.edu.unq.grupoh.criptop2p.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserResponse {
    private String name; //3-30
    private String lastname; //3-30
    private String email; //email format
    private String address; //3-30
    private String password; //1 minuscula,1 mayuscula , 1 caracter especial , Min 6
    private String cvu; //22 digitos
    private String addressWallet ; //8 digitos
    private int amountOperations; // La cantidad de concretaciones de intenciones
    private int points;

    public UserResponse() {
    }

    public UserResponse(String name, String lastname, String email, String address, String password, String cvu, String addressWallet, int amountOperations, int points) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.addressWallet = addressWallet;
        this.amountOperations = amountOperations;
        this.points = points;
    }
}
