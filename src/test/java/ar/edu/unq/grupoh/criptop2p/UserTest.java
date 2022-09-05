package ar.edu.unq.grupoh.criptop2p;
import ar.edu.unq.grupoh.criptop2p.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void createOneUserSuccessful() {
        User user1 = User.build(0,"pe","argento","asdsadsa@gmail","asdsadsad","aAsadsadsad.","1234567891234567891233","12345678");
        assertEquals(user1.getName(),"pe");
        assertEquals(user1.getLastname(),"argento");
        assertEquals(user1.getEmail(),"asdsadsa@gmail");
    }
}