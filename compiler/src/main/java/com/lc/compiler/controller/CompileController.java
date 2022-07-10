package com.lc.compiler.controller;

import com.lc.Result;
import com.lc.compiler.enums.ResultCodeEnum;
import com.lc.compiler.model.vo.JobVO;
import com.lc.compiler.model.vo.RequestCodeVO;
import com.lc.compiler.service.EntryService;
import com.lc.compiler.util.CrsfUtils;
import com.lc.compiler.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 3:50 PM
 */
@RequestMapping("/just")
@RestController
public class CompileController {

    private EntryService entryService;

    @Autowired
    public void setServices(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping("/run")
    public ResultData<?> run(@RequestBody @Validated RequestCodeVO requestCodeVO) {

        // crsf
        if (!CrsfUtils.match(requestCodeVO.getCrsfToken())) {
            return ResultData.error("invalid crsf_token ");
        }

        JobVO run = entryService.run(requestCodeVO);

        return ResultData.success("等待执行结果", run);
    }

    @GetMapping("/query")
    public ResultData<?> query(@RequestParam String id) throws ExecutionException, InterruptedException {


        JobVO query = entryService.query(id);

        if (query.getStatus() == -1) {
            return ResultData.error("No result", query);
        }
        if (query.getStatus() == 1) {
            return ResultData.success("程序仍在运行中", query);
        }

        ResultData<Object> resultData = ResultData.success("");
        resultData.setData(query);

        Result result = query.getResult();
        // 未知问题
        if (result == null) {
            resultData.setMsg(ResultCodeEnum.getStatusMsg(3));
            return resultData;
        }
        // 0 ok, 1 编译过程有错误, 2 运行过程有错误
        if (result.getStatus() != 0) {
            resultData.setMsg(ResultCodeEnum.getStatusMsg(result.getStatus()));
        }

        // 编译错误的 result也是4, 就不用设置msg
        if (result.getResult() != 4) {
            resultData.setMsg(resultData.getMsg() + ResultCodeEnum.getResultMsg(result.getResult()));
        }

        return resultData;

    }
}
