package ar.edu.unq.grupoh.criptop2p.model;

import ar.edu.unq.grupoh.criptop2p.dto.request.UserRequest;
import ar.edu.unq.grupoh.criptop2p.exceptions.UserException;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private int userId;
    @Getter
    private String name; //3-30
    @Getter
    private String lastname; //3-30
    @Getter
    private String email; //email format
    @Getter
    private String address; //3-30
    @Getter
    private String password; //1 minuscula,1 mayuscula , 1 caracter especial , Min 6
    @Getter
    private String cvu; //22 digitos
    @Getter
    private String addressWallet ; //8 digitos
    @Getter @Setter
    private int amountOperations = 0; // La cantidad de concretaciones de intenciones
    @Getter @Setter
    private int points = 0;


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

    public static User build(UserRequest userRequest) throws UserException {
        return User.builder()
                .withName(      userRequest.getName())
                .withLastname(  userRequest.getLastname())
                .withEmail(     userRequest.getEmail())
                .withPassword(  userRequest.getPassword())
                .withAddress(   userRequest.getAddress())
                .withCvu(       userRequest.getCvu())
                .withWallet(    userRequest.getAddressWallet())
                .build();
    }

    public void addPoint(int x){
        this.points += x;
        this.amountOperations ++;
    }

    public void substractPoints(){
        this.points -= 20;
    }

    public void setName(String name) throws UserException {
        Validator.nameMatches(name);
        this.name = name;
    }

    public void setLastname(String lastName) throws UserException {
        Validator.nameMatches(lastName);
        this.lastname = lastName;
    }

    public void setEmail(String email) throws UserException {
        Validator.patternMatches(email);
        this.email = email;
    }

    public void setPassword(String password) throws UserException {
        Validator.patternMatchesPassword(password);
        this.password = password;
    }

    public void setAddress(String address) throws UserException {
        Validator.addressMatches(address);
        this.address = address;
    }

    public void setWallet(String wallet) throws UserException {
        Validator.walletMatches(wallet);
        this.addressWallet = wallet;
    }

    public void setCvu(String cvu) throws UserException {
        Validator.cvuMatches(cvu);
        this.cvu = cvu;
    }

    public static final class UserBuilder {
        private final User user = new User();

        private UserBuilder() {}

        public UserBuilder withName(String name) throws UserException {
            user.setName(name);
            return this;
        }

        public UserBuilder withLastname(String lastName) throws UserException {
            user.setLastname(lastName);
            return this;
        }

        public UserBuilder withEmail(String email) throws UserException {
            user.setEmail(email);
            return this;
        }

        public UserBuilder withPassword(String password) throws UserException {
            user.setPassword(password);
            return this;
        }

        public UserBuilder withAddress(String address) throws UserException {
            user.setAddress(address);
            return this;
        }

        public UserBuilder withCvu(String cvu) throws UserException {
            user.setCvu(cvu);
            return this;
        }

        public UserBuilder withWallet(String wallet) throws UserException {
            user.setWallet(wallet);
            return this;
        }

        public UserBuilder withId(Integer id) throws UserException {
            user.setUserId(id);
            return this;
        }

        public User build() {
            return user;
        }
    }

    public static UserBuilder builder(){
        return new UserBuilder();
    }
}
