package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.api.dto.PurchaseFileResponse;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.exception.PurchaseFileProcessingException;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.*;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Service
@Primary
@Slf4j
public class PurchaseFileTxtService implements PurchaseFileService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private PurchaserRepository purchaserRepository;
    @Autowired
    private PurchaseFileRepository purchaseFileRepository;

    @Override
    public void readFile(MultipartFile file) {
        PurchaseFile purchaseFile = handleFileUpload(file);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("Arquivo vazio");
            }

            String line;
            int readLines = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                readLines += 1;

                String purchaserName = values[0];
                String itemDescription = values[1];
                BigDecimal itemPrice = new BigDecimal(values[2]);
                Integer purchaseCount = Integer.parseInt(values[3]);
                String merchantAddress = values[4];
                String merchantName = values[5];

                createPurchase(purchaserName, merchantName, merchantAddress,
                        itemDescription, itemPrice, purchaseCount, purchaseFile);
            }

            if (readLines == 0) {
                throw new IOException("Arquivo vazio");
            }

            log.info("[PurchaseFileTxtService] :: Quantidade de linhas lidas: {}", readLines);
        } catch (Exception e) {
            throw new PurchaseFileProcessingException(e.getMessage());
        }
    }

    @Transactional
    private PurchaseFile handleFileUpload(MultipartFile file) {
        try {
            PurchaseFile purchaseFile = new PurchaseFile();
            purchaseFile.setFileName(file.getOriginalFilename());
            return purchaseFileRepository.save(purchaseFile);
        } catch (DataIntegrityViolationException e) {
            throw new PurchaseFileProcessingException("Arquivo já importado: " + file.getOriginalFilename());
        }
    }

    @Transactional
    private void createPurchase(String purchaserName, String merchantName, String merchantAddress,
                                String itemDescription, BigDecimal itemPrice, Integer purchaseCount,
                                PurchaseFile purchaseFile) {
        Purchaser purchaser = purchaserRepository.findByName(purchaserName)
                .orElseGet(() -> {
                    Purchaser newPurchaser = new Purchaser();
                    newPurchaser.setName(purchaserName);
                    return purchaserRepository.save(newPurchaser);
                });

        Merchant merchant = merchantRepository.findByNameAndAddress(merchantName, merchantAddress)
                .orElseGet(() -> {
                    Merchant newMerchant = new Merchant();
                    newMerchant.setName(merchantName);
                    newMerchant.setAddress(merchantAddress);
                    return merchantRepository.save(newMerchant);
                });

        Item item = itemRepository.findByDescriptionAndPrice(itemDescription, itemPrice)
                .orElseGet(() -> {
                    Item newItem = new Item();
                    newItem.setDescription(itemDescription);
                    newItem.setPrice(itemPrice);
                    return itemRepository.save(newItem);
                });

        Purchase purchase = new Purchase();
        purchase.setPurchaser(purchaser);
        purchase.setMerchant(merchant);
        purchase.setItem(item);
        purchase.setPurchaseCount(purchaseCount);
        purchase.setPurchaseFile(purchaseFile);

        purchaseRepository.save(purchase);
    }

    public List<PurchaseFileResponse> findAllPurchaseFilesSummarized() {
        return purchaseFileRepository.findAllSummarized().orElse(null);
    }

}
