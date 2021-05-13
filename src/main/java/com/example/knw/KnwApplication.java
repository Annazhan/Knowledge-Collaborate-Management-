package com.example.knw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Repository;

@SpringBootApplication(scanBasePackages = {"com.example.knw"})
@EntityScan(basePackages = "com.example.knw.pojo")
@ServletComponentScan
@MapperScan(
        basePackages = "com.example.knw.*",
        annotationClass = Repository.class
)
public class KnwApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnwApplication.class, args);
    }

}
