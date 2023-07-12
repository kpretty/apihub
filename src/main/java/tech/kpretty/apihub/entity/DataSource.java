package tech.kpretty.apihub.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ds")
public class DataSource {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 数据源名称
    private String name;

    // 数据源连接
    @TableField(value = "jdbc_url")
    private String jdbcUrl;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 描述
    private String description;

    // 创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 修改时间
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
