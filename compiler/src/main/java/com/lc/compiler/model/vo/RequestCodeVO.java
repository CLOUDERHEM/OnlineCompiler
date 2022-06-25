package com.lc.compiler.model.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:45 PM
 */
@Data
public class RequestCodeVO {
    @Max(value = 4)
    @Min(value = 1)
    @NotNull(message = "language 不能为空")
    private Integer language;
    private String input;
    @NotEmpty(message = "code 不能为空")
    private String code;
    private String crsfToken;
}
