package com.lc.compiler.model;

import com.lc.Result;
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
