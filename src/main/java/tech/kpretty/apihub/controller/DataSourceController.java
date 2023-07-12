package tech.kpretty.apihub.controller;

import tech.kpretty.apihub.entity.DataSource;
import tech.kpretty.apihub.result.Result;
import tech.kpretty.apihub.result.ResultResponse;
import tech.kpretty.apihub.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjun
 * <br/>对接口绑定的数据源的增删改查
 * @since 1.0.0
 */

@Slf4j
@RestController
@RequestMapping("/ds")
public class DataSourceController {
    @Resource
    DataSourceService dataSourceService;

    @PostMapping("")
    public Result add(@RequestBody @Validated DataSource ds) {
        try {
            dataSourceService.add(ds);
            log.info("添加数据源:\n\tname:{}\n\tjdbcUrl:{}\n\tdesc:{}", ds.getName(), ds.getJdbcUrl(), ds.getDescription());
            return ResultResponse.success();
        } catch (Exception e) {
            log.error("添加数据源失败", e);
            return ResultResponse.error("数据源添加失败", e.getMessage());
        }
    }

    @GetMapping("")
    public Result all() {
        List<DataSource> all = dataSourceService.all();
        return ResultResponse.success(all);
    }

    @PutMapping("")
    public Result update(@RequestBody @Validated DataSource ds) {
        dataSourceService.update(ds);
        return ResultResponse.success();
    }

    @PostMapping("/test")
    public Result testConnect(@RequestBody @Validated DataSource ds) {
        if (dataSourceService.testConnect(ds)) {
            return ResultResponse.success();
        } else {
            return ResultResponse.error("failed", null);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        try {
            dataSourceService.delete(id);
            log.info("删除数据源: {}", id);
            return ResultResponse.success();
        } catch (Exception e) {
            log.error("删除数据源失败", e);
            return ResultResponse.error("数据源删除失败", e.getMessage());
        }
    }
}
