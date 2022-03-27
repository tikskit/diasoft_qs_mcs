package ru.diasoft.digitalq.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.digitalq.domain.SmsVerification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Репозиторий smsVerification должен")
class SmsVerificationRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SmsVerificationRepository repository;

    @DisplayName("сохранять сущности SmsVerification в БД.")
    @Test
    void shouldSaveEntities() {
        SmsVerification smsVerification = SmsVerification.builder()
                .phonenumber("1234655")
                .secretcode("secret")
                .processguid("59936767-1cf5-459d-a516-8377e56724f5")
                .build();
        SmsVerification saved = repository.save(smsVerification);

        SmsVerification actual = em.find(SmsVerification.class, saved.getVerificationid());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(saved);
    }
}