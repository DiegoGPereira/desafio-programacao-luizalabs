package br.com.magazineluiza.desafio_programacao_luizalabs.unit.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.PurchaseRepository;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.TxtPurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TxtPurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private TxtPurchaseService txtPurchaseService;

    @Test
    void testReadFile() throws IOException {
        String content = "Header\nTeste\tItem 1\t10.00\t2\t123 Street\tMerchant A";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

        List<Purchase> result = txtPurchaseService.readFile(file);

        assertNotNull(result);
        assertEquals(1, result.size());
        Purchase purchase = result.getFirst();
        assertEquals("Teste", purchase.getPurchaserName());
        assertEquals("Item 1", purchase.getItemDescription());
        assertEquals(new BigDecimal("10.00"), purchase.getItemPrice());
        assertEquals(2, purchase.getPurchaseCount());
        assertEquals("123 Street", purchase.getMerchantAddress());
        assertEquals("Merchant A", purchase.getMerchantName());
        assertNotNull(purchase.getCorrelationId());
    }

    @Test
    void testReadFileWithEmptyFile() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "".getBytes());

        assertThrows(RuntimeException.class, () -> txtPurchaseService.readFile(file));
    }

    @Test
    void testSaveAll() {
        List<Purchase> purchases = Arrays.asList(new Purchase(), new Purchase());
        txtPurchaseService.saveAll(purchases);
        verify(purchaseRepository, times(1)).saveAll(purchases);
    }

    @Test
    void testFindByCorrelationId() {
        UUID correlationId = UUID.randomUUID();
        List<Purchase> expectedPurchases = Arrays.asList(new Purchase(), new Purchase());
        when(purchaseRepository.findByCorrelationId(correlationId)).thenReturn(expectedPurchases);

        List<Purchase> result = txtPurchaseService.findByCorrelationId(correlationId.toString());

        assertEquals(expectedPurchases, result);
        verify(purchaseRepository, times(1)).findByCorrelationId(correlationId);
    }

    @Test
    void testFindAllUploadedFiles() {
        List<PurchaseFilesResponse> expectedResponse = Arrays.asList(
                new PurchaseFilesResponse(UUID.randomUUID(), LocalDate.now(), BigDecimal.TWO, BigDecimal.TWO),
                new PurchaseFilesResponse(UUID.randomUUID(), LocalDate.now(), BigDecimal.TEN, BigDecimal.TEN));

        when(purchaseRepository.findAllUploadedFiles()).thenReturn(expectedResponse);

        List<PurchaseFilesResponse> result = txtPurchaseService.findAllUploadedFiles();

        assertEquals(expectedResponse, result);
        verify(purchaseRepository, times(1)).findAllUploadedFiles();
    }
}
