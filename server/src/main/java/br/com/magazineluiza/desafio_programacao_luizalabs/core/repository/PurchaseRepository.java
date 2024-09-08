package br.com.magazineluiza.desafio_programacao_luizalabs.core.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByCorrelationId(UUID correlationId);

    @Query(value = "SELECT new br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse" +
            "(correlationId as correlationId, CAST(inclusionDate AS LocalDate)," +
            " SUM(itemPrice * purchaseCount), SUM(SUM(itemPrice * purchaseCount)) OVER ()) " +
            " FROM Purchase " +
            "GROUP BY correlationId, CAST(inclusionDate AS LocalDate)")
    List<PurchaseFilesResponse> findAllUploadedFiles();

}
