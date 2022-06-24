package com.lc.compiler.controller;

import com.lc.Result;
import com.lc.compiler.enums.ResultCodeEnum;
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
@RequestMapping("/just")
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
        ResultData<Result> resultData = ResultData.success("success", result);

        if (result == null) {
            resultData.setMsg(ResultCodeEnum.getStatusMsg(3));
            return resultData;
        }

        if (result.getStatus() != 0) {
            resultData.setMsg(ResultCodeEnum.getStatusMsg(result.getStatus()));
            return resultData;
        }

        resultData.setMsg(ResultCodeEnum.getResultMsg(result.getResult()));

        return resultData;
    }
}
