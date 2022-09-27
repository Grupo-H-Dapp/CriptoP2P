package ar.edu.unq.grupoh.criptop2p.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private int userId;
    private String name; //3-30
    private String lastname; //3-30
    private String email; //email format
    private String address; //3-30
    private String password; //1 minuscula,1 mayuscula , 1 caracter especial , Min 6
    private String cvu; //22 digitos
    private String addressWallet ; //8 digitos
    private int amountOperations; // La cantidad de concretaciones de intenciones
    private int points;

    public void addPoint(int x){
        this.points += x;
        this.amountOperations ++;
    }

    public void addOperation() {
        this.amountOperations ++;
    }

    public void substractPoints(){
        this.points -= 20;
    }

    public User(String name, String lastname, String email, String address, String password, String cvu, String addressWallet, int amountOperations, int points) {
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
