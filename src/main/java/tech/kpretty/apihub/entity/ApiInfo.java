package tech.kpretty.apihub.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("api")
public class ApiInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField(value = "name_zh")
    private String nameZh;

    @TableField(value = "ds_id")
    private Long dsId;

    private String query;

    /* 接口缓存 */
    @TableField(value = "enable_cache")
    private boolean enableCache;

    @TableField(value = "cache_ttl")
    private Integer cacheTTL;

    private String description;

    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 修改时间
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
