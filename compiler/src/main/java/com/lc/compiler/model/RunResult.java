package com.lc.compiler.model;

import com.lc.Result;
import lombok.Data;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:00 PM
 */
@Data
public class RunResult extends Result {
    private String output;
    private String runMsg;
}
