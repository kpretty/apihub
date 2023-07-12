package tech.kpretty.apihub.service.impl;

import tech.kpretty.apihub.entity.ApiInfo;
import tech.kpretty.apihub.mapper.ApiInfoMapper;
import tech.kpretty.apihub.service.ApiInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApiInfoServiceImpl implements ApiInfoService {
    @Resource
    ApiInfoMapper apiInfoMapper;

    @Override
    public void add(ApiInfo apiInfo) {
        // 稍微处理一下 sql
        String query = apiInfo.getQuery();
        if (query.endsWith(";")) {
            apiInfo.setQuery(query.substring(0, query.length() - 1));
        }
        apiInfoMapper.insert(apiInfo);
    }
}
