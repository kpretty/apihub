package tech.kpretty.apihub.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tech.kpretty.apihub.entity.Query;
import tech.kpretty.apihub.result.PageResult;
import tech.kpretty.apihub.result.Result;
import tech.kpretty.apihub.result.ResultResponse;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@Slf4j
@Aspect
@Component
public class AspectCache {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    ObjectMapper mapper;

    @Pointcut("@annotation(EnableCache)")
    public void point() { /*no-op*/}

    @Around("point()")
    public Object cache(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0 && args[0] instanceof Query) {
            Query query = (Query) args[0];
            // 判断接口是否开启了缓存
            if (!query.getApiInfo().isEnableCache()) {
                return pjp.proceed();
            }
            /*下面是接口缓存的逻辑*/
            // step-1 判断是否命中缓存
            String key = buildRedisKey(query);
            try {
                if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                    log.info("query:[{}] 命中缓存", query.getApiInfo().getName());
                    String cache = redisTemplate.opsForValue().getAndExpire(key, query.getApiInfo().getCacheTTL(), TimeUnit.SECONDS);
                    return mapper.readValue(cache, PageResult.class);
                } else {
                    // step-2 没有命中缓存
                    PageResult proceed = (PageResult) pjp.proceed();
                    // step-3 将结果缓存到 redis
                    String cache = mapper.writeValueAsString(proceed);
                    redisTemplate.opsForValue().set(key, cache, query.getApiInfo().getCacheTTL(), TimeUnit.SECONDS);
                    return proceed;
                }
            } catch (Exception e) {
                log.error("缓存服务不可用, 错误原因: {}", e.getMessage());
                return pjp.proceed();
            }
        } else {
            log.warn("该方法暂不支持缓存");
            return pjp.proceed();
        }
    }


    private String buildRedisKey(Query query) {
        // 接口名称 + 动态参数 + 分页参数
        if (query.getPageParam().isEnablePage()) {
            return String.format("%s_%s_%s", query.getApiInfo().getName(), query.getParams(), query.getPageParam());
        } else {
            return String.format("%s_%s", query.getApiInfo().getName(), query.getParams());
        }
    }
}
