package tech.kpretty.apihub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tech.kpretty.apihub.entity.ApiInfo;
import tech.kpretty.apihub.entity.DataSource;
import tech.kpretty.apihub.entity.PageParam;
import tech.kpretty.apihub.entity.Query;
import tech.kpretty.apihub.exception.ApiException;
import tech.kpretty.apihub.mapper.ApiInfoMapper;
import tech.kpretty.apihub.mapper.DataSourceMapper;
import tech.kpretty.apihub.proxy.EnableCache;
import tech.kpretty.apihub.result.MetaData;
import tech.kpretty.apihub.result.PageResult;
import tech.kpretty.apihub.service.QueryService;
import tech.kpretty.apihub.util.ConnectPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.*;

@Service
@Slf4j
public class QueryServiceImpl implements QueryService {
    @Resource
    ApiInfoMapper apiInfoMapper;

    @Resource
    DataSourceMapper dataSourceMapper;

    @Resource
    ConnectPool pool;

    @Override
    public Query buildQuery(String name, Map<String, Object> params, PageParam pageParam) throws ApiException {
        // 获取 api 信息
        ApiInfo apiInfo = apiInfoMapper.selectOne(
                new LambdaQueryWrapper<ApiInfo>()
                        .eq(ApiInfo::getName, name)
        );
        // 校验 api 是否存在
        Optional.ofNullable(apiInfo).orElseThrow(() -> new ApiException(MessageFormat.format("api 接口[{0}]不存在", name)));

        // 根据 api 获取 ds 信息
        DataSource dataSource = dataSourceMapper.selectById(apiInfo.getDsId());

        return new Query(apiInfo, dataSource, params, pageParam);
    }

    @Override
    @EnableCache
    public PageResult query(Query query) throws SQLException {
        query.formatQuery();
        PageResult result = new PageResult();
        // 从 pool 中获取一个数据库连接
        Connection connection = pool.get(query.getDs());
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        Long count;
        // 分页查询
        if (query.getPageParam().isEnablePage()) {
            // 分页查询时计算 count 值
            count = pageCount(query.getApiInfo().getQuery(), statement);
            PageParam pageParam = query.getPageParam();
            int pageNum = pageParam.getPageNum();
            int pageSize = pageParam.getPageSize();
            //  设置分页信息
            result.setMetaData(new MetaData((pageNum - 1) * pageSize, pageNum * pageSize, count, pageNum, pageSize));
            String sql = String.format("select * from ( %s ) t limit %d, %d", query.getApiInfo().getQuery(), (pageNum - 1) * pageSize, pageSize);
            resultSet = statement.executeQuery(sql);
        } else {
            resultSet = statement.executeQuery(query.getApiInfo().getQuery());
        }
        // 获取结果集元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 用于保存查询结果
        List<Map<String, Object>> data = new ArrayList<>();
        while (resultSet.next()) {
            HashMap<String, Object> node = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                node.put(columnName, value);
            }
            data.add(node);
        }
        close(resultSet, statement, connection);
        result.setData(data);

        return result;
    }

    private Long pageCount(String sql, Statement sta) throws SQLException {
        ResultSet resultSet = sta.executeQuery(String.format("select count(1) as num from ( %s ) t", sql));
        resultSet.next();
        long count = resultSet.getLong("num");
        resultSet.close();
        return count;
    }

    private void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            try {
                closeable.close();
            } catch (Exception e) {
                log.warn("数据库资源关闭异常");
            }
        }
    }
}
