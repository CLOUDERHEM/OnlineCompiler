package com.lc.compiler.controller;

import com.lc.Result;
import com.lc.compiler.model.CompileResult;
import com.lc.compiler.model.RequestCodeVO;
import com.lc.compiler.service.Server;
import com.lc.compiler.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 3:50 PM
 */
@RequestMapping("/api")
@RestController
public class CompileController {

    private Server compileService;

    @Autowired
    public void setCompileService(Server compileService) {
        this.compileService = compileService;
    }

    @PostMapping("/run")
    public ResultData<?> run(@RequestBody @Validated RequestCodeVO requestCodeVO) {

        Result result = compileService.serve(requestCodeVO);

        if (result == null || result.getStatus() == 3) {
            return ResultData.error("系统错误, 请联系开发者", result);
        }
        if (result.getStatus() == 1) {
            return ResultData.error("编译错误", result);
        }
        if (result.getResult() == 2) {
            return ResultData.error("运行时错误", result);
        }
        return ResultData.success("success", result);
    }
}
