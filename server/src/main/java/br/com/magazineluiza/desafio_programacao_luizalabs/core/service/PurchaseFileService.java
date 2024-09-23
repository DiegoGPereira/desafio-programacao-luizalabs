package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PurchaseFileService extends PurchaseFileReader<MultipartFile> {

    List<PurchaseFileResponse> findAllPurchaseFilesSummarized();

}
