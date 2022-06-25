package com.lc.compiler.service.impl;

import com.lc.Result;
import com.lc.compiler.model.vo.JobVO;
import com.lc.compiler.model.vo.RequestCodeVO;
import com.lc.compiler.service.EntryService;
import com.lc.compiler.service.ExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * todo, map数据过多问题
 *
 * @author Aaron Yeung
 * @date 6/25/2022 12:27 PM
 */
@Service
public class EntryServiceImpl implements EntryService {

    private static final Map<String, Future<Result>> JOB_LIST = new ConcurrentHashMap<>(50);

    private ExecService execService;
    private ThreadPoolTaskExecutor threadPoolExecutor;

    @Autowired
    public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Autowired
    public void setExecService(ExecService execService) {
        this.execService = execService;
    }

    @Override
    public JobVO run(RequestCodeVO requestCodeVO) {

        JobVO job = new JobVO();
        String dirName = UUID.randomUUID().toString();
        job.setId(dirName);
        job.setTimestamp(System.currentTimeMillis());

        // submit task
        Future<Result> submit = threadPoolExecutor.submit(() -> execService.exec(requestCodeVO, dirName));

        JOB_LIST.put(dirName, submit);

        return job;

    }

    @Override
    public JobVO query(String id) throws ExecutionException, InterruptedException {

        JobVO job = new JobVO(id, -1, System.currentTimeMillis());

        if (JOB_LIST.isEmpty()) {
            return job;
        }

        Future<Result> future = JOB_LIST.get(id);

        if (!future.isDone()) {
            job.setStatus(1);
        } else {
            job.setStatus(0);
            job.setResult(future.get());
        }

        return job;
    }
}
