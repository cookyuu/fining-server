package com.hklim.finingserver.domain.auth.service;

import com.hklim.finingserver.domain.auth.dto.EmailAuthCodeRequestDto;
import com.hklim.finingserver.domain.auth.dto.EmailAuthenticationRequestDto;
import com.hklim.finingserver.global.entity.RedisKeyType;
import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import com.hklim.finingserver.global.utils.AuthUtils;
import com.hklim.finingserver.global.utils.RedisUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtils redisUtils;
    private final AuthUtils authUtils;
    @Transactional
    public void sendEmail(EmailAuthCodeRequestDto request) {
        log.info("[SEND EMAIL AUTH-CODE PROCESS] START");
        String authKey = authUtils.createAuthCode();
        saveAuthCode(request.getEmail(), authKey);
        sendAuthEmail(request.getEmail(), authKey);
        log.info("[SEND EMAIL AUTH-CODE PROCESS] END");
    }

    @Transactional
    public void authenticateEmail(EmailAuthenticationRequestDto request) {

        String email = request.getEmail();
        String authCode = request.getAuthCode();

        log.info("[AUTHENTICATE EMAIL PROCESS] Email authenticate START {email : {}", email + "}");
        String saveAuthCode = redisUtils.getData(RedisKeyType.AUTH_EMAIL.getSeparator() + email);

        if (saveAuthCode == null) {
            throw new ApplicationErrorException(ApplicationErrorType.EMPTY_RESULT_DATA_ERROR, "[Email Authentication Process] Authentication Code is timeout");
        }
        if (!saveAuthCode.equals(authCode)) {
            throw new ApplicationErrorException(ApplicationErrorType.DATA_MATCHING_FAIL, "[Email Authentication Process] Authentication Code is not matched");
        }
        redisUtils.deleteData(email);
        log.info("[AUTHENTICATE EMAIL PROCESS] Email authenticate END, SUCCESS! {email : {}", email + "}");
    }

    private void sendAuthEmail(String email, String authKey) {
        String subject = "[FINING] 이메일 인증을 위한 인증번호입니다.";
        String text = "메일 인증을 위한 인증번호는 " + authKey + " 입니다. <br/>";
        try {
            log.info("[SEND EMAIL AUTH-CODE PROCESS] Send email START {email : {}",email + "}");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mimeMessage);
            log.info("[SEND EMAIL AUTH-CODE PROCESS] Send email END {email : {}",email +"}");
        } catch (MessagingException e) {
            throw new ApplicationErrorException(ApplicationErrorType.AUTHCODE_SEND_ERROR, e);
        }
    }

    private void saveAuthCode(String email, String authKey) {
        log.info("[SEND EMAIL AUTH-CODE PROCESS] Save AuthCode START");
        try {
            redisUtils.setDataExpire(RedisKeyType.AUTH_EMAIL.getSeparator() + email, authKey, 60*3L);
        } catch (Exception e) {
            throw new ApplicationErrorException(ApplicationErrorType.FAIL_TO_SAVE_DATA, e);
        }
        log.info("[SEND EMAIL AUTH-CODE PROCESS] Save AuthCode END");
    }

}
