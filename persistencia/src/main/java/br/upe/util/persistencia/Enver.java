package br.upe.util.persistencia;

import io.github.cdimascio.dotenv.Dotenv;

public class Enver {
    public void setDevelopEnv(DevelopEntityManagerFactory developEntityManagerFactory) {
        developEntityManagerFactory.setEnv(getDevelopEnv());
    }

    public void setDefaultEnv(DefaultEntityManagerFactory defaultEntityManagerFactory) {
        defaultEntityManagerFactory.setEnv(getDefaultEnv());
    }
    public Dotenv getDefaultEnv(){
        return Dotenv.configure()
                .directory(".")
                .filename(".env")
                .load();
    }
    public Dotenv getDevelopEnv(){
        return Dotenv.configure()
                .directory("..")
                .filename(".env")
                .load();
    }
}
