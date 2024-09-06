package br.com.magazineluiza.desafio_programacao_luizalabs.core.service;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.Purchase;

import java.util.List;

public interface PurchaseReader<T> {

    List<Purchase> readFile(T file);

}
