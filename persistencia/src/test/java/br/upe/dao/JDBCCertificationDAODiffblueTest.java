package br.upe.dao;

import static org.mockito.Mockito.mock;
import br.upe.entities.Certification;
import br.upe.entities.Event;
import br.upe.util.persistencia.PersistenciaInterface;
import org.junit.jupiter.api.Assertions;
import br.upe.util.persistencia.LambdaEntityManagerFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
class JDBCCertificationDAODiffblueTest {
    /**
     * Test
     * {@link JDBCCertificationDAO#JDBCCertificationDAO(LambdaEntityManagerFactory)}.
     */
    private final Logger logger = Logger.getLogger(JDBCCertificationDAODiffblueTest.class.getName());
    private final JDBCCertificationDAO certificationDAO = PersistenciaInterface.createJDBCCertificationDAO(PersistenciaInterface.getDevelopEMF_lambda());

    @Test
    @DisplayName("Test save method certificationDAO")
    void saveCertificationTest() {
        Certification certification = PersistenciaInterface.createCertification();
        certification.setFilename("certification.txt");
        certification.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now());
        certificationDAO.save(certification);

        Assertions.assertNotEquals(certificationDAO.findById(certification.getId()), Optional.empty());
    }

    @Test
    @DisplayName("Test update method for certificationDAO")
    public void updateCertificationTest() {
        Certification certification = PersistenciaInterface.createCertification();

        certification.setFilename("certification.txt");
        certification.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now());

        certificationDAO.save(certification);

        certification.setFilename("certification2.txt");
        certification.setContent((new String("lots of data 2")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now().plusDays(1));
        certificationDAO.update(certification);

        Assertions.assertEquals(certification.getFilename(), "certification2.txt");
    }

    @Test
    @DisplayName("Test delete method for certificationDAO")
    public void deleteCertificationTest() {
        Certification certification = PersistenciaInterface.createCertification();

        certification.setFilename("certification.txt");
        certification.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now());

        certificationDAO.save(certification);


        Long id = certification.getId();
        certificationDAO.delete(certification);
        Assertions.assertEquals(certificationDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test deleteById method for certificationDAO")
    public void deleteByIdCertificationTest() {
        Certification certification = PersistenciaInterface.createCertification();

        certification.setFilename("certification.txt");
        certification.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now());

        certificationDAO.save(certification);


        Long id = certification.getId();
        certificationDAO.deleteById(id);
        Assertions.assertEquals(certificationDAO.findById(id), Optional.empty());
    }

    @Test
    @DisplayName("Test findById method for certificationDAO")
    public void findByIdCertificationTest() {
        Certification certification = PersistenciaInterface.createCertification();

        certification.setFilename("certification.txt");
        certification.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now());

        certificationDAO.save(certification);

        Long id = certification.getId();
        Assertions.assertEquals(certificationDAO.findById(id).get(), certification);
    }

    @Test
    @DisplayName("Test findAll method for certificationDAO")
    public void findAllCertificationTest() {
        Certification certification = PersistenciaInterface.createCertification();

        certification.setFilename("certification.txt");
        certification.setContent((new String("lots of data")).getBytes(StandardCharsets.UTF_8));
        certification.setDate(LocalDate.now());

        certificationDAO.save(certification);

        List<Certification> certifications = certificationDAO.findAll();
        Assertions.assertFalse(certifications.isEmpty());
    }
}
