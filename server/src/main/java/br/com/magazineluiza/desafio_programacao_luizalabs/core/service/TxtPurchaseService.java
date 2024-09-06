package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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

            String[] headers = headerLine.split("\t");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");

                Purchase purchase = new Purchase();

                for (int i = 0; i < headers.length; i++) {
                    String columnName = headers[i].toLowerCase();
                    String value = values[i];
                    setFieldValue(purchase, columnName, value);
                }

                purchases.add(purchase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return purchases;
    }

    private void setFieldValue(Purchase purchase, String fieldName, String value) {
        try {
            Field field = Purchase.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == String.class) {
                field.set(purchase, value);
            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                field.set(purchase, Integer.parseInt(value));
            } else if (field.getType() == BigDecimal.class) {
                field.set(purchase, new BigDecimal(value));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void saveAll(List<Purchase> purchases) {
        purchaseRepository.saveAll(purchases);
    }

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public List<Purchase> findByCorrelationId(String correlationId) {
        return purchaseRepository.findByCorrelationId(UUID.fromString(correlationId));
    }

    @Override
    public BigDecimal gross() {
        List<Purchase> purchases = this.findAll();
        return purchases.stream()
                .map(Purchase::getItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
