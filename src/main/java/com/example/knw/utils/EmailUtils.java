package com.example.knw.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 发送邮件工具类
 *
 * @author qanna
 * @date 2021-04-06
 */
@Slf4j
public class EmailUtils {

    private static String ranges = "0123456789";

    // 发送邮件
    public static boolean sendMail(String receiver, String subject, String verifyCode) {
        StringBuilder content = new StringBuilder();
        content.append("<body>");
        content.append("<p>");
        content.append("欢迎注册HUST知识协同管理系统！专注团队合作，提高整体效率<br/><br/>");
        content.append("<b>您的验证码是"+verifyCode+"</b>，验证码将在10分钟后过期<br/><br/><br/>");
        content.append("如果您未进行操作，请忽略此邮件");
        content.append("</p>");
        content.append("</body>");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");

        try{
            message.setFrom("knw_hust@163.com");    // 这里设置邮件发送方
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(content.toString(),true);  // 这里设置邮件内容
        }catch (MessagingException e){
            e.printStackTrace();
            log.info("后端错误：设置邮件信息失败");
            return false;
        }

        try{
            // 配置发送方邮箱信息
            javaMailSender.setHost("smtp.163.com");     // 邮箱服务器地址，这里是163邮箱服务器
            javaMailSender.setUsername("knw_hust@163.com"); // 发送方邮箱地址，必须和上面setFrom一致
            javaMailSender.setPassword("EVRJRXQJHTMRCUWS"); // smtp服务授权码
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
            log.info("后端错误: EmailUtils.sendMail");
            return false;
        }
        log.info("验证码发送成功: email:" + receiver + "  verifyCode:" + verifyCode);
        return true;
    }

    public static String generateVerifyCode(){
        int rangesLen = ranges.length();
        String code = "";
        Random random = new Random();
        for(int i = 0; i < 6; i++){
            int index = random.nextInt(rangesLen);
            code += ranges.charAt(index);
        }
        return code;
    }

    public static boolean isEmail(String s){
        String p = "^[\\w.%+-]+@[\\w-\\.]+[\\w]{2,6}$";
        Pattern pattern = Pattern.compile(p);
        if(pattern.matcher(s).matches()){
            return true;
        }
        return false;
    }
}
