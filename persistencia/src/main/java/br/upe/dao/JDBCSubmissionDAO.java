package br.upe.dao;

import br.upe.entities.Submission;
import br.upe.util.persistencia.LambdaEntityManagerFactory;

public class JDBCSubmissionDAO extends JDBCGenericDAO<Submission, Long> {
    public JDBCSubmissionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Submission.class);
        this.createEntityManager = lambdaFunction;
        this.openEM();
    }
}
