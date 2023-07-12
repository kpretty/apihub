package tech.kpretty.apihub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 预留配置用于修改默认的数据库连接池
 */
@Component
@ConfigurationProperties(prefix = "apihub")
public class ConnectPoolConfig {

}
