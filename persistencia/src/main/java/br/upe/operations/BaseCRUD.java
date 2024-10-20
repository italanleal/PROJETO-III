package br.upe.operations;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseCRUD {
    private static final Logger logger = Logger.getLogger(BaseCRUD.class.getName());
    public BaseCRUD(String pathname) {
        File d = new File(".\\state");
        if (!d.exists()) {
            boolean mkdirs;
            do {
                 mkdirs = d.mkdir();
            } while(!mkdirs);
        }

       File f = new File(pathname);
        if (!f.exists()) {
            try {
                boolean createfile;
                do {
                    createfile = f.createNewFile();
                } while(createfile);

            } catch (IOException e) {
                logger.log(Level.SEVERE, ("Erro ao criar o arquivo" + pathname), e);
            }
        }
    }
}