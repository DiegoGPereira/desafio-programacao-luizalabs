package br.com.magazineluiza.desafio_programacao_luizalabs.unit.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.UnsupportedFileTypeException;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileReader;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileServiceFactory;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.service.PurchaseFileTxtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseFileServiceFactoryTest {

    @Mock
    private PurchaseFileTxtService txtReader;

    @InjectMocks
    private PurchaseFileServiceFactory purchaseFileServiceFactory;

    @Test
    void testGetReaderForTxtFile() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        PurchaseFileReader<MultipartFile> result = purchaseFileServiceFactory.getReader(file);
        assertEquals(txtReader, result);
    }

    @Test
    void testGetReaderForTabFile() {
        MultipartFile file = new MockMultipartFile("file", "test.tab", "text/plain", "content".getBytes());
        PurchaseFileReader<MultipartFile> result = purchaseFileServiceFactory.getReader(file);
        assertEquals(txtReader, result);
    }

    @Test
    void testGetReaderForUnsupportedFile() {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        assertThrows(UnsupportedFileTypeException.class, () -> purchaseFileServiceFactory.getReader(file));
    }

    @Test
    void testGetReaderForNullFilename() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(null);
        assertThrows(UnsupportedFileTypeException.class, () -> purchaseFileServiceFactory.getReader(file));
    }
}