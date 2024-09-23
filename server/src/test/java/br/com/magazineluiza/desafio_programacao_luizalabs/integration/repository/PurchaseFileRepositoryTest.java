package br.com.magazineluiza.desafio_programacao_luizalabs.integration.repository;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFileResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.*;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.PurchaseFileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PurchaseFileRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PurchaseFileRepository purchaseFileRepository;

    @Test
    void testFindAllSummarized() {
        PurchaseFile purchaseFile1 = new PurchaseFile();
        purchaseFile1.setFileName("file1.txt");
        purchaseFile1.setInclusionDate(LocalDateTime.now());

        PurchaseFile purchaseFile2 = new PurchaseFile();
        purchaseFile2.setFileName("file2.txt");
        purchaseFile2.setInclusionDate(LocalDateTime.now());

        Item item1 = new Item();
        item1.setDescription("Item 1");
        item1.setPrice(new BigDecimal("10.00"));

        Item item2 = new Item();
        item2.setDescription("Item 2");
        item2.setPrice(new BigDecimal("20.00"));

        Purchaser purchaser = new Purchaser();
        purchaser.setName("John Doe");

        Merchant merchant = new Merchant();
        merchant.setName("Store A");
        merchant.setAddress("123 Main St");

        Purchase purchase1 = new Purchase();
        purchase1.setPurchaseFile(purchaseFile1);
        purchase1.setItem(item1);
        purchase1.setPurchaser(purchaser);
        purchase1.setMerchant(merchant);
        purchase1.setPurchaseCount(2);

        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseFile(purchaseFile2);
        purchase2.setItem(item2);
        purchase2.setPurchaser(purchaser);
        purchase2.setMerchant(merchant);
        purchase2.setPurchaseCount(1);

        entityManager.persist(purchaseFile1);
        entityManager.persist(purchaseFile2);
        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(purchaser);
        entityManager.persist(merchant);
        entityManager.persist(purchase1);
        entityManager.persist(purchase2);
        entityManager.flush();

        Optional<List<PurchaseFileResponse>> result = purchaseFileRepository.findAllSummarized();

        assertTrue(result.isPresent());
        List<PurchaseFileResponse> responses = result.get();
        assertEquals(2, responses.size());

        PurchaseFileResponse response1 = responses.stream()
                .filter(r -> r.fileName().equals("file1.txt"))
                .findFirst()
                .orElseThrow();
        assertEquals(new BigDecimal("20.00"), response1.gross());

        PurchaseFileResponse response2 = responses.stream()
                .filter(r -> r.fileName().equals("file2.txt"))
                .findFirst()
                .orElseThrow();
        assertEquals(new BigDecimal("20.00"), response2.gross());
    }
}