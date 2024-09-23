package br.com.magazineluiza.desafio_programacao_luizalabs.core.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    Optional<Merchant> findByNameAndAddress(String merchantName, String merchantAddress);

}
