package ar.edu.unq.grupoh.criptop2p.repositories;


import ar.edu.unq.grupoh.criptop2p.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    List<User> findAll();

    User findByUserId(int id);
}