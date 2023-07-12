package tech.kpretty.apihub.service.impl;

import tech.kpretty.apihub.entity.DataSource;
import tech.kpretty.apihub.mapper.DataSourceMapper;
import tech.kpretty.apihub.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class DataSourceServiceImpl implements DataSourceService {
    @Resource
    DataSourceMapper dataSourceMapper;

    @Override
    public void add(DataSource ds) {
        dataSourceMapper.insert(ds);
    }

    @Override
    public void delete(Long id) {
        dataSourceMapper.deleteById(id);
    }

    @Override
    public void update(DataSource ds) {
        dataSourceMapper.updateById(ds);
    }

    @Override
    public List<DataSource> all() {
        return dataSourceMapper.selectList(null);
    }

    @Override
    public boolean testConnect(DataSource ds) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(ds.getJdbcUrl(), ds.getUsername(), ds.getPassword());
        } catch (SQLException e) {
            log.error("测试数据库连接不通过", e);
            return false;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            // no-op
        }
        return true;

    }
}
