package br.upe.operations;

import java.io.File;

public class BaseCRUD {
    public BaseCRUD() {
        File d = new File(".\\state");
        if (!d.exists()) {
            boolean mkdirs;
            do {
                 mkdirs = d.mkdir();
            } while(!mkdirs);
        }
    }
}