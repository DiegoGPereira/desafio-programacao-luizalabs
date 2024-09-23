package br.com.magazineluiza.desafio_programacao_luizalabs.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class PurchaseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String fileName;

    private LocalDateTime inclusionDate;

    @OneToMany(mappedBy = "purchaseFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Purchase> purchases = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        inclusionDate = LocalDateTime.now();
    }

}
