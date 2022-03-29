package ru.diasoft.digitalq.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.digitalq.domain.SmsVerification;
import ru.diasoft.digitalq.domain.SmsVerificationCheckRequest;
import ru.diasoft.digitalq.domain.SmsVerificationCheckResponse;
import ru.diasoft.digitalq.domain.SmsVerificationPostRequest;
import ru.diasoft.digitalq.domain.SmsVerificationPostResponse;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис smsVerification должен")
@SpringBootTest
@ActiveProfiles("test")
@Import(SmsVerificationServiceImpl.class)
class SmsVerificationServiceImplTest {
    @Configuration
    public static class Config {

    }

    @MockBean
    private SmsVerificationRepository repository;

    @Autowired
    private SmsVerificationServiceImpl service;

    @DisplayName("создавать заявку на sms верификацию")
    @Test
    void shouldGenerateNecessaryFields() {
        String phoneNumber = "123456";
        SmsVerificationPostRequest req = new SmsVerificationPostRequest(phoneNumber);
        SmsVerification saved = SmsVerification.builder()
                .phonenumber(phoneNumber)
                .secretcode("secret")
                .processguid("59936767-1cf5-459d-a516-8377e56724f5")
                .status("waiting")
                .verificationid(1L)
                .build();
        when(repository.save(any())).thenReturn(saved);

        SmsVerificationPostResponse resp = service.dsSmsVerificationCreate(req);

        assertThat(resp.getProcessGUID()).isNotEmpty();
    }

    @DisplayName("выполнять верификацию, если требуемая заявка существует")
    @Test
    void shouldVerifyWhenRequestExistsAndCorrect() {
        String secretCode = "789";
        String procguid = "59936767-1cf5-459d-a516-8377e56724f5";
        Optional<SmsVerification> ver = Optional.of(SmsVerification.builder().processguid(procguid).build());
        when(repository.findBySecretcode(secretCode)).thenReturn(ver);

        SmsVerificationCheckRequest req = new SmsVerificationCheckRequest(procguid, secretCode);

        SmsVerificationCheckResponse actual = service.dsSmsVerificationCheck(req);
        assertThat(actual).isNotNull();
        assertThat(actual.getCheckResult()).isTrue();
    }
    @DisplayName("не выполнять верификацию, если требуемая заявка существует, но guid не совпадает")
    @Test
    void shouldNotVerifyWhenRequestExistsButIncorrect() {
        String secretCode = "789";
        String expectingGuid = "59936767-1cf5-459d-a516-8377e56724f5";
        Optional<SmsVerification> ver = Optional.of(SmsVerification.builder().processguid(expectingGuid).build());
        when(repository.findBySecretcode(secretCode)).thenReturn(ver);

        String actualGuid = "59936767-1cf5-459d-a516-8377e56724333";
        SmsVerificationCheckRequest req = new SmsVerificationCheckRequest(actualGuid, secretCode);

        SmsVerificationCheckResponse actual = service.dsSmsVerificationCheck(req);
        assertThat(actual).isNotNull();
        assertThat(actual.getCheckResult()).isFalse();
    }

    @DisplayName("не выполнять верификацию, если заяви не существует")
    @Test
    void shouldNotVerifyWhenRequestNotExists() {
        String secretCode = "789";
        when(repository.findBySecretcode(secretCode)).thenReturn(Optional.empty());

        String actualGuid = "59936767-1cf5-459d-a516-8377e56724333";
        SmsVerificationCheckRequest req = new SmsVerificationCheckRequest(actualGuid, secretCode);

        SmsVerificationCheckResponse actual = service.dsSmsVerificationCheck(req);
        assertThat(actual).isNotNull();
        assertThat(actual.getCheckResult()).isFalse();
    }
}