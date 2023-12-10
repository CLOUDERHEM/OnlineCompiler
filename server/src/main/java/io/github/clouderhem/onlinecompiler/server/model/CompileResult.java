package io.github.clouderhem.onlinecompiler.server.model;

import io.github.clouderhem.executor.Result;
import lombok.Data;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:49 PM
 */
@Data
public class CompileResult extends Result {
    private String compileMsg;
    private String exePath;

}
