package tech.kpretty.apihub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageParam {
    private boolean enablePage;
    private int pageNum;
    private int pageSize;

    @Override
    public String toString() {
        return String.format("%s_%s", pageNum, pageSize);
    }
}
