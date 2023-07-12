package tech.kpretty.apihub.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private MetaData metaData;
    private Object data;
}
