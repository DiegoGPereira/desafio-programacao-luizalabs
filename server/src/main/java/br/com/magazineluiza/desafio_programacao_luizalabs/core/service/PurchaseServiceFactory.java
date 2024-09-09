package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.UnsupportedFileTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PurchaseServiceFactory {

    @Autowired
    private TxtPurchaseService txtReader;

    public PurchaseReader<MultipartFile> getReader(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new UnsupportedFileTypeException("Nome do arquivo não encontrado");
        }

        if (filename.endsWith(".tab") || filename.endsWith(".txt")) {
            return txtReader;
        }  // another types ex: csv
        String fileExtension = filename.substring(filename.lastIndexOf('.') + 1);
        throw new UnsupportedFileTypeException(fileExtension);
    }

}
