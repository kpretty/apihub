package tech.kpretty.apihub.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存储接口元数据
 * 当前版本仅存储分页查询时的元数据信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    /*分页信息*/
    private Integer start;
    private Integer end;
    private Long count;
    private Integer pageNum;
    private Integer pageSize;
}
