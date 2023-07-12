package tech.kpretty.apihub.controller;

import tech.kpretty.apihub.entity.PageParam;
import tech.kpretty.apihub.exception.ApiException;
import tech.kpretty.apihub.proxy.EnableCache;
import tech.kpretty.apihub.result.PageResult;
import tech.kpretty.apihub.result.Result;
import tech.kpretty.apihub.result.ResultResponse;
import tech.kpretty.apihub.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/query")
public class QueryController {
    @Resource
    QueryService queryService;

    @PostMapping("/{name}")
    public Result query(@PathVariable("name") String name,
                        @RequestBody Map<String, Object> params,
            /* 作为大数据查询的接口平台，分页查询的效率将是极低的*/
            /*因此如果是复杂的聚合查询不要开启分页，仅简单的 select 允许*/
                        @RequestParam(value = "enablePage", required = false, defaultValue = "false") Boolean enablePage,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {
            PageResult result = queryService.query(queryService.buildQuery(name, params, new PageParam(enablePage, pageNum, pageSize)));
            if (result.getMetaData() != null) {
                return ResultResponse.success(result.getMetaData(), result.getData());
            } else {
                return ResultResponse.success(result.getData());
            }
        } catch (SQLException e) {
            log.error("查询数据异常", e);
            return ResultResponse.error("查询数据异常", e.getMessage());
        } catch (ApiException e) {
            log.error("api 状态异常", e);
            return ResultResponse.error("api 状态异常", e.getMessage());
        }
    }
}
