package br.com.magazineluiza.desafio_programacao_luizalabs.core.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchaser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaserRepository extends JpaRepository<Purchaser, UUID> {

    Optional<Purchaser> findByName(String purchaserName);

}
