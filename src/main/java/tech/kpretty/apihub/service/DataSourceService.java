package tech.kpretty.apihub.service;

import tech.kpretty.apihub.entity.DataSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataSourceService {
    void add(DataSource ds);

    void delete(Long id);

    void update(DataSource ds);

    List<DataSource> all();

    boolean testConnect(DataSource ds);

}
