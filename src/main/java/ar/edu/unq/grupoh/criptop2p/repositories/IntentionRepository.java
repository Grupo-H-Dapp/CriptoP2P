package ar.edu.unq.grupoh.criptop2p.repositories;

import ar.edu.unq.grupoh.criptop2p.model.Intention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntentionRepository extends JpaRepository<Intention,Long> {
}