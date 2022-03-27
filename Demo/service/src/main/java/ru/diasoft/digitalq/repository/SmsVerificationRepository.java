package ru.diasoft.digitalq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.diasoft.digitalq.domain.SmsVerification;

@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {

}
