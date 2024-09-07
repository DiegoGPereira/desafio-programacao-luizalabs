package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFilesResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TxtPurchaseService implements PurchaseServiceOperations {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> readFile(MultipartFile file) {
        List<Purchase> purchases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("Header not found or empty file");
            }

            String line;
            UUID correlationId = UUID.randomUUID();
            while ((line = br.readLine()) != null) {
                Purchase purchase = getPurchase(line, correlationId);
                purchases.add(purchase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return purchases;
    }

    private static Purchase getPurchase(String line, UUID correlationId) {
        String[] values = line.split("\t");

        Purchase purchase = new Purchase();
        purchase.setPurchaserName(values[0]);
        purchase.setItemDescription(values[1]);
        purchase.setItemPrice(new BigDecimal(values[2]));
        purchase.setPurchaseCount(Integer.parseInt(values[3]));
        purchase.setMerchantAddress(values[4]);
        purchase.setMerchantName(values[5]);
        purchase.setCorrelationId(correlationId);
        return purchase;
    }

    @Override
    @Transactional
    public void saveAll(List<Purchase> purchases) {
        purchaseRepository.saveAll(purchases);
    }

    @Override
    public List<Purchase> findByCorrelationId(String correlationId) {
        return purchaseRepository.findByCorrelationId(UUID.fromString(correlationId));
    }

    @Override
    public List<PurchaseFilesResponse> findAllUploadedFiles() {
        return purchaseRepository.findAllUploadedFiles();
    }

}
