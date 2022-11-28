package ar.edu.unq.grupoh.criptop2p.dto.response;

import ar.edu.unq.grupoh.criptop2p.model.User;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {
    @Getter@Setter
    private int id;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String lastname;
    @Getter@Setter
    private String email;
    @Getter@Setter
    private String address;
    @Getter@Setter
    private String cvu;
    @Getter@Setter
    private String addressWallet;
    private  User wrapped;

    public UserResponse() {
    }

    public UserResponse(User wrapped) {
        this.wrapped = wrapped;
        this.id = wrapped.getUserId();
        this.name = wrapped.getName();
        this.lastname = wrapped.getLastname();
        this.email = wrapped.getEmail();
        this.address = wrapped.getAddress();
        this.cvu = wrapped.getCvu();
        this.addressWallet = wrapped.getAddressWallet();
    }

}
