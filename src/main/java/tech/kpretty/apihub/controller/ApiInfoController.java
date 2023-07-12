package tech.kpretty.apihub.controller;

import tech.kpretty.apihub.entity.ApiInfo;
import tech.kpretty.apihub.result.Result;
import tech.kpretty.apihub.result.ResultResponse;
import tech.kpretty.apihub.service.ApiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiInfoController {
    @Resource
    ApiInfoService apiInfoService;

    @PostMapping("")
    public Result add(@RequestBody @Validated ApiInfo apiInfo) {
        try {
            apiInfoService.add(apiInfo);
            return ResultResponse.success();
        } catch (Exception e) {
            log.error("添加 api 错误", e);
            return ResultResponse.error("添加 api 错误", e.getMessage());
        }
    }
}
