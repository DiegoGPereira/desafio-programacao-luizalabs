package br.com.magazineluiza.desafio_programacao_luizalabs.unit.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.PurchaseFileProcessingException;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.*;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.*;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileTxtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseFileTxtServiceTest {

    @InjectMocks
    private PurchaseFileTxtService purchaseFileTxtService;

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private PurchaserRepository purchaserRepository;
    @Mock
    private PurchaseFileRepository purchaseFileRepository;

    @Test
    void testReadFile() throws IOException {
        String fileContent = "Comprador\tDescrição\tPreço Unitário\tQuantidade\tEndereço\tFornecedor\n" +
                "João Silva\tR$10 off R$20 of food\t10.0\t2\t987 Fake St\tBob's Pizza";
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));

        when(purchaseFileRepository.save(any(PurchaseFile.class))).thenReturn(new PurchaseFile());
        when(purchaserRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(purchaserRepository.save(any(Purchaser.class))).thenReturn(new Purchaser());
        when(merchantRepository.findByNameAndAddress(anyString(), anyString())).thenReturn(Optional.empty());
        when(merchantRepository.save(any(Merchant.class))).thenReturn(new Merchant());
        when(itemRepository.findByDescriptionAndPrice(anyString(), any(BigDecimal.class))).thenReturn(Optional.empty());
        when(itemRepository.save(any(Item.class))).thenReturn(new Item());
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase());

        assertDoesNotThrow(() -> purchaseFileTxtService.readFile(file));

        verify(purchaseFileRepository, times(1)).save(any(PurchaseFile.class));
        verify(purchaserRepository, times(1)).findByName(anyString());
        verify(purchaserRepository, times(1)).save(any(Purchaser.class));
        verify(merchantRepository, times(1)).findByNameAndAddress(anyString(), anyString());
        verify(merchantRepository, times(1)).save(any(Merchant.class));
        verify(itemRepository, times(1)).findByDescriptionAndPrice(anyString(), any(BigDecimal.class));
        verify(itemRepository, times(1)).save(any(Item.class));
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    void testReadFileWithEmptyFile() {
        MultipartFile emptyFile = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);

        assertThrows(PurchaseFileProcessingException.class, () -> purchaseFileTxtService.readFile(emptyFile));
    }

    @Test
    void testReadFileWithInvalidContent() {
        String invalidContent = "Invalid\tContent\n";
        MultipartFile invalidFile = new MockMultipartFile("file", "invalid.txt", "text/plain", invalidContent.getBytes(StandardCharsets.UTF_8));

        when(purchaseFileRepository.save(any(PurchaseFile.class))).thenReturn(new PurchaseFile());

        assertThrows(PurchaseFileProcessingException.class, () -> purchaseFileTxtService.readFile(invalidFile));
    }
}
