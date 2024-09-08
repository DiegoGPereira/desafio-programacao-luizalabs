package br.com.magazineluiza.desafio_programacao_luizalabs.integration.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PurchaseRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    void testFindByCorrelationId() {
        UUID correlationId = UUID.randomUUID();
        Purchase purchase1 = createPurchase(correlationId, "Item 1", new BigDecimal("10.00"));
        Purchase purchase2 = createPurchase(correlationId, "Item 2", new BigDecimal("20.00"));
        Purchase purchase3 = createPurchase(UUID.randomUUID(), "Item 3", new BigDecimal("30.00"));

        entityManager.persist(purchase1);
        entityManager.persist(purchase2);
        entityManager.persist(purchase3);
        entityManager.flush();

        List<Purchase> foundPurchases = purchaseRepository.findByCorrelationId(correlationId);

        assertEquals(2, foundPurchases.size());
        assertTrue(foundPurchases.stream().allMatch(p -> p.getCorrelationId().equals(correlationId)));
    }

    @Test
    void testFindAllUploadedFiles() {
        UUID correlationId1 = UUID.randomUUID();
        UUID correlationId2 = UUID.randomUUID();

        Purchase purchase1 = createPurchase(correlationId1, "Item 1", new BigDecimal("10.00"));
        Purchase purchase2 = createPurchase(correlationId1, "Item 2", new BigDecimal("20.00"));
        Purchase purchase3 = createPurchase(correlationId2, "Item 3", new BigDecimal("30.00"));

        entityManager.persist(purchase1);
        entityManager.persist(purchase2);
        entityManager.persist(purchase3);
        entityManager.flush();

        List<PurchaseFilesResponse> responses = purchaseRepository.findAllUploadedFiles();

        assertEquals(2, responses.size());
        BigDecimal grossByCorrelationId1 = responses.stream()
                .filter(purchaseFilesResponse -> purchaseFilesResponse.correlationId().equals(correlationId1))
                .map(PurchaseFilesResponse::grossByCorrelationId)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        assertEquals(new BigDecimal("30.00"), grossByCorrelationId1);
        assertEquals(new BigDecimal("60.00"), responses.getFirst().totalGross());
    }

    private Purchase createPurchase(UUID correlationId, String itemDescription, BigDecimal itemPrice) {
        Purchase purchase = new Purchase();
        purchase.setCorrelationId(correlationId);
        purchase.setPurchaserName("Teste");
        purchase.setItemDescription(itemDescription);
        purchase.setItemPrice(itemPrice);
        purchase.setPurchaseCount(1);
        purchase.setMerchantAddress("123 Street");
        purchase.setMerchantName("Merchant A");
        purchase.setInclusionDate(LocalDateTime.now());
        return purchase;
    }
}