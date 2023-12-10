package io.github.clouderhem.onlinecompiler.server.service;

import io.github.clouderhem.onlinecompiler.server.model.vo.JobVO;
import io.github.clouderhem.onlinecompiler.server.model.vo.RequestCodeVO;

import java.util.concurrent.ExecutionException;

/**
 * @author Aaron Yeung
 * @date 6/25/2022 11:06 AM
 */

public interface EntryService {

    /**
     * 运行代码入口
     *
     * @param requestCodeVO requestCodeVO
     * @return job
     */
    JobVO run(RequestCodeVO requestCodeVO);

    /**
     * 通过 id 查询运行结果
     *
     * @param id id
     * @return job
     * @throws ExecutionException   ex
     * @throws InterruptedException ex
     */
    JobVO query(String id) throws ExecutionException, InterruptedException;
}
