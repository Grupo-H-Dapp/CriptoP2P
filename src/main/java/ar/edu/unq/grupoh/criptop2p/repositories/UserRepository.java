package ar.edu.unq.grupoh.criptop2p.repositories;


import ar.edu.unq.grupoh.criptop2p.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    List<User> findAll();

}
