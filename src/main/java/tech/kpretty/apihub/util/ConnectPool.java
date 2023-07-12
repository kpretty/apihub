package tech.kpretty.apihub.util;

import tech.kpretty.apihub.entity.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectPool {
    private final ConcurrentHashMap<Long, HikariDataSource> pool;

    public ConnectPool() {
        this.pool = new ConcurrentHashMap<>();
    }

    public Connection get(DataSource ds) throws SQLException {
        // 判断数据源是否存在，不存在创建数据源
        if (!pool.containsKey(ds.getId())) {
            pool.put(ds.getId(), createDataSource(ds));
        }
        return pool.get(ds.getId()).getConnection();
    }

    private HikariDataSource createDataSource(DataSource ds) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("HikariPool-" + ds.getName());
        hikariConfig.setJdbcUrl(ds.getJdbcUrl());
        hikariConfig.setUsername(ds.getUsername());
        hikariConfig.setPassword(ds.getPassword());
        return new HikariDataSource(hikariConfig);
    }

    @PreDestroy
    public void shutdown() {
        pool.values().forEach(HikariDataSource::close);
    }

}
