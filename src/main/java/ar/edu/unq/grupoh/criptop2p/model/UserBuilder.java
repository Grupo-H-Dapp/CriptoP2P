package ar.edu.unq.grupoh.criptop2p.model;

public class UserBuilder {
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private String addressWallet;
    private int amountOperations;
    private int points;
    private int userId;

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withCvu(String cvu) {
        this.cvu = cvu;
        return this;
    }

    public UserBuilder withAddressWallet(String addressWallet) {
        this.addressWallet = addressWallet;
        return this;
    }

    public UserBuilder withAmountOperations(int amountOperations) {
        this.amountOperations = amountOperations;
        return this;
    }

    public UserBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public UserBuilder withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public User createUser() {
        return new User(name, lastname, email, address, password, cvu, addressWallet, amountOperations, points);
    }
}