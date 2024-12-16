package br.upe.facade;

import java.util.Random;

public class TestingFeatures {
    public static String randomAlphaDecimalText(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder resultado = new StringBuilder(tamanho);
        Random random = new Random();

        for (int i = 0; i < tamanho; i++) {
            int indice = random.nextInt(caracteres.length());
            resultado.append(caracteres.charAt(indice));
        }
        return resultado.toString();
    }
}
