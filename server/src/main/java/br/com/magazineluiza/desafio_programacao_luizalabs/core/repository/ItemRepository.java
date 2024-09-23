package br.com.magazineluiza.desafio_programacao_luizalabs.core.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Optional<Item> findByDescriptionAndPrice(String itemDescription, BigDecimal itemPrice);

}
