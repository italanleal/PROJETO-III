package br.upe.util.persistencia;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private Dotenv env;
    public static String get(String key) {
        return env.get(key);
    }
    public EnvConfig(boolean develop){
        env = Dotenv.configure()
                .directory("..")
                .filename(".env")
                .load();
    }
    public EnvConfig(){
        env = Dotenv.configure()
                .directory(".")
                .filename(".env")
                .load();
    }
}
