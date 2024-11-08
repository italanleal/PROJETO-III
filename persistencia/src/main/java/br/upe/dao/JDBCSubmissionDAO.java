package br.upe.dao;

import br.upe.entities.Submission;
import br.upe.util.LambdaEntityManagerFactory;

public class JDBCSubmissionDAO extends JDBCGenericDAO<Submission, Long> {
    public JDBCSubmissionDAO(LambdaEntityManagerFactory lambdaFunction) {
        super(Submission.class);
        this.createEntityManager = lambdaFunction;
    }
}
