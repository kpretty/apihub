package tech.kpretty.apihub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("tech.kpretty.apihub.mapper")
@SpringBootApplication
public class ApihubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApihubApplication.class, args);
    }

}
