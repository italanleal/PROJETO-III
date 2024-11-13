package br.upe.dao;

import br.upe.entities.Certification;
import br.upe.util.persistencia.LambdaEntityManagerFactory;

public class JDBCCertificationDAO extends JDBCGenericDAO<Certification, Long> {
    public JDBCCertificationDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Certification.class);
        this.createEntityManager = lambdaFunction;
    }
}
