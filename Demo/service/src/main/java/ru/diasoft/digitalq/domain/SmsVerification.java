package ru.diasoft.digitalq.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sms_verification")
@Data
@Builder
public class SmsVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_verification_id_seq")
    @SequenceGenerator(name = "sms_verification_id_seq", sequenceName = "sms_verification_id_seq")
    @Column(name = "verificationid")
    private Long verificationid;

    @Column(name = "processguid")
    private String processguid;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "secretcode")
    private String secretcode;

    @Column(name = "status")
    private String status;
}
