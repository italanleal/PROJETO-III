package br.upe.operations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import br.upe.exceptions.HashingException;

public interface HasherInterface {
    static String hash(String message) {
        try {
            // Cria a instância MessageDigest para SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Adiciona os bytes da senha
            byte[] bytes = md.digest(message.getBytes());

            // Converte bytes para o formato hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            // Retorna a senha hashed
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Lança uma exceção personalizada com uma mensagem específica
            throw new HashingException("Erro ao gerar hash: Algoritmo SHA-256 não encontrado.", e);
        }
    }
}
