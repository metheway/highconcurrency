package event_bus;


import java.util.concurrent.ExecutorService;

public class AsyncEventBus extends EventBus {
    private static final String DEFAULT_ASYNC_NAME = "default_async_bus";

    public AsyncEventBus(String busName, ExecutorService executorService) {
        this(busName, null, executorService);
    }

    public AsyncEventBus(String busName, EventExceptionHandler eventExceptionHandler, ExecutorService executorService) {
        super(DEFAULT_ASYNC_NAME, eventExceptionHandler, executorService);
    }

    public AsyncEventBus(ExecutorService executorService) {
        this(DEFAULT_ASYNC_NAME, null, executorService);
    }

    public AsyncEventBus(ExecutorService executorService, EventExceptionHandler eventExceptionHandler) {
        this(DEFAULT_ASYNC_NAME, eventExceptionHandler, executorService);
    }

}
