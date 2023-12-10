package io.github.clouderhem.onlinecompiler.server.model.vo;

import io.github.clouderhem.executor.Result;
import lombok.Data;

/**
 * @author Aaron Yeung
 * @date 6/25/2022 11:13 AM
 */
@Data
public class JobVO {
    private String id;
    private Integer status;
    private Result result;
    private Long timestamp;

    public JobVO() {

    }

    public JobVO(String id, Integer status, Long timestamp) {
        this.id = id;
        this.status = status;
        this.timestamp = timestamp;
    }
}
