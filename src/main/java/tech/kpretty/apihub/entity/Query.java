package tech.kpretty.apihub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Query {
    private ApiInfo apiInfo;

    // 绑定的数据源
    private DataSource ds;

    // sql 动态参数
    private Map<String, Object> params;

    // 分页配置
    private PageParam pageParam;


    public void formatQuery() {
        if (params != null) {
            String sql = apiInfo.getQuery();
            for (String key : params.keySet()) {
                sql = sql.replace(String.format("${%s}", key), params.get(key).toString());
            }
            apiInfo.setQuery(sql);
        }
    }
}
