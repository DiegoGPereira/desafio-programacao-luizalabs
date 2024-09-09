package br.com.magazineluiza.desafio_programacao_luizalabs.unit.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.controller.PurchaseController;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseServiceFactory;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseServiceOperations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    @Mock
    private PurchaseServiceOperations purchaseService;

    @Mock
    private PurchaseServiceFactory purchaseServiceFactory;

    @InjectMocks
    private PurchaseController purchaseController;

    @Test
    void testUploadFile() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        UUID correlationId = UUID.randomUUID();
        Purchase purchase = new Purchase();
        purchase.setCorrelationId(correlationId);
        List<Purchase> purchases = List.of(purchase);

        when(purchaseService.readFile(file)).thenReturn(purchases);
        when(purchaseServiceFactory.getReader(file)).thenReturn(purchaseService);

        ResponseEntity<UUID> response = purchaseController.uploadFile(file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(correlationId, response.getBody());
        verify(purchaseService).readFile(file);
        verify(purchaseService).saveAll(purchases);
    }

    @Test
    void testFindByCorrelationId() {
        String correlationId = UUID.randomUUID().toString();
        Purchase purchase = new Purchase();
        purchase.setPurchaserName("Teste");
        purchase.setItemDescription("Item 1");
        purchase.setItemPrice(new BigDecimal("10.00"));
        purchase.setPurchaseCount(2);
        purchase.setMerchantAddress("123 Street");
        purchase.setMerchantName("Merchant A");

        when(purchaseService.findByCorrelationId(correlationId)).thenReturn(List.of(purchase));

        ResponseEntity<List<PurchaseResponse>> response = purchaseController.findByCorrelationId(correlationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        PurchaseResponse purchaseResponse = response.getBody().getFirst();
        assertEquals("Teste", purchaseResponse.purchaserName());
        assertEquals("Item 1", purchaseResponse.itemDescription());
        assertEquals(new BigDecimal("10.00"), purchaseResponse.itemPrice());
        assertEquals(2, purchaseResponse.purchaseCount());
        assertEquals("123 Street", purchaseResponse.merchantAddress());
        assertEquals("Merchant A", purchaseResponse.merchantName());
        verify(purchaseService).findByCorrelationId(correlationId);
    }

    @Test
    void testFindAllUploadedFiles() {
        List<PurchaseFilesResponse> expectedResponse = Arrays.asList(
                new PurchaseFilesResponse(UUID.randomUUID(), LocalDate.now(), BigDecimal.TWO, BigDecimal.TWO),
                new PurchaseFilesResponse(UUID.randomUUID(), LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN)
        );
        when(purchaseService.findAllUploadedFiles()).thenReturn(expectedResponse);

        ResponseEntity<List<PurchaseFilesResponse>> response = purchaseController.findAllUploadedFiles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(purchaseService).findAllUploadedFiles();
    }
}
