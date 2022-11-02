package ar.edu.unq.grupoh.criptop2p.repositories;

import ar.edu.unq.grupoh.criptop2p.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Cryptocurrency,Long> {
}
