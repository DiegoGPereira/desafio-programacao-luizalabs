package br.com.magazineluiza.desafio_programacao_luizalabs.core.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFileResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.PurchaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseFileRepository extends JpaRepository<PurchaseFile, UUID> {

    @Query("SELECT new br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFileResponse" +
            "(pf.fileName, pf.inclusionDate, SUM(i.price * p.purchaseCount)) " +
            "FROM PurchaseFile pf " +
            "JOIN pf.purchases p " +
            "JOIN p.item i " +
            "GROUP BY pf.fileName, pf.inclusionDate")
    Optional<List<PurchaseFileResponse>> findAllSummarized();

}
