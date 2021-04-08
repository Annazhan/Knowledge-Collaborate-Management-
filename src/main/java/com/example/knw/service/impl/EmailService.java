package com.example.knw.service.impl;

import com.example.knw.dao.EmailCodeMapper;
import com.example.knw.exception.InvalidEmail;
import com.example.knw.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮箱服务
 *
 * @author qanna
 * @date 2021-04-07
 */
@Service
@Slf4j
public class EmailService {
    @Autowired
    private EmailCodeMapper redisDao;

    // 验证码过期时间，单位为秒
    private static Long Code_Expiration_Time = Integer.toUnsignedLong(10 * 60);

    private static String subject1 = "HUST知识协同管理系统验证码";

    // 发送邮箱验证码
    public boolean sendVerifyCode(String email){
        // 生成随机验证码
        String verifyCode = EmailUtils.generateVerifyCode();
        // 将验证码保存到redis中
        log.info("输入是否可以match："+EmailUtils.isEmail(email));
        if(!EmailUtils.isEmail(email)){
            throw new InvalidEmail();
        }
        try{
            if(redisDao.saveEmailCode(email, verifyCode, Code_Expiration_Time)){
                return EmailUtils.sendMail(email, subject1, verifyCode);
            }else{
                log.info("后端操作失败: 保存验证码到Redis");
                return false;
            }
        }catch (Exception e){
            log.info("后端错误: sendVerifyCode");
            return false;
        }
    }

    // 验证邮箱验证码
    public boolean checkVerifyCode(String email, String VerifyCode){
        String code = redisDao.getEmailCode(email);
        if(code.equals(VerifyCode)){
            return true;
        }else{
            return false;
        }
    }
}
