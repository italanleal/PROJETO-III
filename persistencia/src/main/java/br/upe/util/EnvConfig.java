package br.upe.util;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv env;
    static {
        env = Dotenv.configure()
                .directory("..")
                .filename(".env")
                .load();
    }
    public static String get(String key) {
        return env.get(key);
    }
}
