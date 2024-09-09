package br.com.magazineluiza.desafio_programacao_luizalabs.core.exception;

public class UnsupportedFileTypeException extends RuntimeException {

    public UnsupportedFileTypeException(String fileType) {
        super("Tipo de arquivo não suportado: " + fileType);
    }

}
