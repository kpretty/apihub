package tech.kpretty.apihub.service;

import org.springframework.stereotype.Service;
import tech.kpretty.apihub.entity.PageParam;
import tech.kpretty.apihub.entity.Query;
import tech.kpretty.apihub.exception.ApiException;
import tech.kpretty.apihub.result.PageResult;

import java.sql.SQLException;
import java.util.Map;

@Service
public interface QueryService {
    Query buildQuery(String name, Map<String, Object> params, PageParam pageParam) throws SQLException, ApiException;

    PageResult query(Query query) throws SQLException;
}
