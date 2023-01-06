package truekai.cc.service;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailService {

    /**
     * 发送邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to,String subject, String content, Map<String, String> attachmentMap) throws MessagingException ;



}
