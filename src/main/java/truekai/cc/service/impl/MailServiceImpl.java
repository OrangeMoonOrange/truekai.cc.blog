package truekai.cc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import truekai.cc.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * 小滴课堂,愿景：让技术不再难学
 *
 * @Description
 * @Author 二当家小D
 * @Remark 有问题直接联系我，源码-笔记-技术交流群
 * @Version 1.0
 **/

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    /**
     *  springboot 提供的一个发送邮件的简单抽象，直接注入即可
     */
    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.from}")
    private String from;

    /**
     * 发送邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        //创建一个邮箱消息对象
        SimpleMailMessage message = new SimpleMailMessage();

        //配置邮箱发送人
        message.setFrom(from);

        //邮件的收件人
        message.setTo(to);

        //邮件的主题
        message.setSubject(subject);

        //邮件的内容
        message.setText(content);
        mailSender.send(message);

        log.info("邮件发送成功:{}",message.toString());

    }

    @Override
    public void sendHtmlMail(String to,String subject, String text, Map<String, String> attachmentMap) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        //是否发送的邮件是富文本（附件，图片，html等）
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);//重点，默认为false，显示原始html代码，无效果

        if (attachmentMap != null) {
            attachmentMap.entrySet().stream().forEach(entrySet -> {
                try {
                    File file = new File(entrySet.getValue());
                    if (file.exists()) {
                        messageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        mailSender.send(mimeMessage);
    }
}
