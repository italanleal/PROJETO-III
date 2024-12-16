module persistencia {
    requires jakarta.persistence;
    requires org.apache.openjpa;
    requires io.github.cdimascio.dotenv.java;
    requires static lombok;
    exports br.upe.entities;
    exports br.upe.dao;
    exports br.upe.util.persistencia;
}