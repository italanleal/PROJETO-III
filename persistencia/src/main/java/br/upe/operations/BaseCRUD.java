package br.upe.operations;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

// base para operações CRUD
public class BaseCRUD {
    // cria um logger para registrar informações de log
    private static final Logger logger = Logger.getLogger(BaseCRUD.class.getName());

    // construtor da classe BaseCRUD
    public BaseCRUD() {
        try {
            // define o path do diretório 'state'
            File d = new File(".\\state");

            // verifica se o diretório 'state' já existe
            if (!d.exists()){
                // tenta criar o diretório 'state'
                if (d.mkdirs()){
                    logger.log(Level.INFO, "Diretório 'state' criado com sucesso. ");
                } else {
                    logger.log(Level.SEVERE, "Falha ao criar o diretório 'state'. ");
                }
            }
        } catch (SecurityException e){
            logger.log(Level.SEVERE, "Permissão negada para criar o diretório 'state'. ", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ocorreu um erro inesperado. ", e);
        }
    }
}
