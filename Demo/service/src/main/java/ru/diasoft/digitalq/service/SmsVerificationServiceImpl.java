package ru.diasoft.digitalq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.domain.SmsVerification;
import ru.diasoft.digitalq.domain.SmsVerificationCheckRequest;
import ru.diasoft.digitalq.domain.SmsVerificationCheckResponse;
import ru.diasoft.digitalq.domain.SmsVerificationPostRequest;
import ru.diasoft.digitalq.domain.SmsVerificationPostResponse;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Primary
@RequiredArgsConstructor
public class SmsVerificationServiceImpl implements SmsVerificationService {

    private final SmsVerificationRepository repository;

    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(SmsVerificationCheckRequest smsVerificationCheckRequest) {
        Optional<SmsVerification> secretCode = repository.findBySecretcode(smsVerificationCheckRequest.getCode());
        return secretCode.map(smsVerification ->
                new SmsVerificationCheckResponse(smsVerification.getProcessguid().equals(smsVerificationCheckRequest.getProcessGUID()))
        ).orElseGet(() -> new SmsVerificationCheckResponse(false));
    }

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(SmsVerificationPostRequest smsVerificationPostRequest) {
        SmsVerification saved = repository.save(SmsVerification.builder()
                .phonenumber(smsVerificationPostRequest.getPhoneNumber())
                .processguid(UUID.randomUUID().toString())
                .secretcode(String.format("%04d", ThreadLocalRandom.current().nextInt()))
                .status("WAITING")
                .build());
        return new SmsVerificationPostResponse(saved.getProcessguid());
    }
}
