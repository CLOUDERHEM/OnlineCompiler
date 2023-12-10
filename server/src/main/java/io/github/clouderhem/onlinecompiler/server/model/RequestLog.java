package io.github.clouderhem.onlinecompiler.server.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:35 PM
 */
@Slf4j
@Builder
@Data
public class RequestLog {
    private String url;
    private String description;
    private String httpMethod;
    private String classMethod;
    private String ip;
    private String requestArgs;
}
