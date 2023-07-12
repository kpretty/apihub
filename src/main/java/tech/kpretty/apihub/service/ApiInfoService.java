package tech.kpretty.apihub.service;

import tech.kpretty.apihub.entity.ApiInfo;
import org.springframework.stereotype.Service;

@Service
public interface ApiInfoService {
    void add(ApiInfo apiInfo);
}
