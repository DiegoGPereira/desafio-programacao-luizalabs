package br.com.magazineluiza.desafio_programacao_luizalabs.unit.api.controller;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.controller.PurchaseController;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseMapper;
import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchaseMapper purchaseMapper;

    @Test
    void testFindAllByFileName_EmptyList() {
        String fileName = "empty.txt";
        when(purchaseService.findByFileName(fileName)).thenReturn(List.of());

        ResponseEntity<List<PurchaseResponse>> response = purchaseController.findAllByFileName(fileName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(purchaseService, times(1)).findByFileName(fileName);
        verify(purchaseMapper, never()).toPurchaseResponse(any(Purchase.class));
    }

    @Test
    void testFindAllByFileName_ServiceThrowsException() {
        String fileName = "nonexistent.txt";
        when(purchaseService.findByFileName(fileName)).thenThrow(new IllegalArgumentException("Arquivo não encontrado"));

        assertThrows(IllegalArgumentException.class, () -> purchaseController.findAllByFileName(fileName));

        verify(purchaseService, times(1)).findByFileName(fileName);
        verify(purchaseMapper, never()).toPurchaseResponse(any(Purchase.class));
    }
}