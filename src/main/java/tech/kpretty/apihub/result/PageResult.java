package tech.kpretty.apihub.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResult {
    private Object data;
    private MetaData metaData;
}
