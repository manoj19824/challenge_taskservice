package com.celonis.challenge.services.async;

import com.celonis.challenge.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTaskExecutor {

    private final Logger logger = LoggerFactory.getLogger(AsyncTaskExecutor.class);

    @Async
    public Future<Boolean> execute(AsyncTaskConstructor asyncTaskGenerator) {
        logger.info("Worker thread is {}",Thread.currentThread().getName());
        asyncTaskGenerator.async();
        return new AsyncResult<Boolean>(true);
    }
}
