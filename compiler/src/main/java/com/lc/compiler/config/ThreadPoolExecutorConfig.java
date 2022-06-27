package com.lc.compiler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Aaron Yeung
 * @date 6/25/2022 10:34 AM
 */
@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolTaskExecutor asyncServiceExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(SystemConfig.CORE_POOL_SIZE);
        executor.setMaxPoolSize(SystemConfig.MAX_POOL_SIZE);
        executor.setQueueCapacity(SystemConfig.QUEUE_CAPACITY);
        executor.setThreadNamePrefix("compile-and-run-thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }
}
