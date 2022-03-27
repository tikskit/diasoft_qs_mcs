package ru.diasoft.digitalq.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.domain.SmsVerification;
import ru.diasoft.digitalq.domain.SmsVerificationCheckRequest;
import ru.diasoft.digitalq.domain.SmsVerificationCheckResponse;
import ru.diasoft.digitalq.domain.SmsVerificationPostRequest;
import ru.diasoft.digitalq.domain.SmsVerificationPostResponse;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;

@Service
@Primary
public class SmsVerificationServiceImpl implements SmsVerificationService {
    private SmsVerificationRepository repository;
    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(SmsVerificationCheckRequest smsVerificationCheckRequest) {
        return null;
    }

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(SmsVerificationPostRequest smsVerificationPostRequest) {
        SmsVerification saved = repository.save(SmsVerification.builder()
                .phonenumber(smsVerificationPostRequest.getPhoneNumber())
                .build());
        return new SmsVerificationPostResponse(saved.getProcessguid());
    }
}
