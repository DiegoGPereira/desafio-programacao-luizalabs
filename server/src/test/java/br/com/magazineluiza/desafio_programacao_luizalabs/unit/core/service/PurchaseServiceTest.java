package br.com.magazineluiza.desafio_programacao_luizalabs.unit.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.PurchaseRepository;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Test
    void testFindByFileName_Success() {
        String fileName = "test.txt";
        List<Purchase> expectedPurchases = Arrays.asList(new Purchase(), new Purchase());
        when(purchaseRepository.findByPurchaseFile_FileName(fileName)).thenReturn(Optional.of(expectedPurchases));

        List<Purchase> result = purchaseService.findByFileName(fileName);

        assertNotNull(result);
        assertEquals(expectedPurchases, result);
        verify(purchaseRepository, times(1)).findByPurchaseFile_FileName(fileName);
    }

    @Test
    void testFindByFileName_EmptyList() {
        String fileName = "empty.txt";
        when(purchaseRepository.findByPurchaseFile_FileName(fileName)).thenReturn(Optional.of(Collections.emptyList()));

        assertThrows(IllegalArgumentException.class, () -> purchaseService.findByFileName(fileName));
        verify(purchaseRepository, times(1)).findByPurchaseFile_FileName(fileName);
    }

    @Test
    void testFindByFileName_NotFound() {
        String fileName = "nonexistent.txt";
        when(purchaseRepository.findByPurchaseFile_FileName(fileName)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> purchaseService.findByFileName(fileName));
        verify(purchaseRepository, times(1)).findByPurchaseFile_FileName(fileName);
    }
}
