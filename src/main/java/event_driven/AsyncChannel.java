package event_driven;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 事件驱动的框架，并发情况下可以多线程处理事件
public abstract class AsyncChannel implements Channel<Event> {
    private final ExecutorService executorService;

    public AsyncChannel() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

    public AsyncChannel(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void dispatch(Event message) {
        executorService.submit(() -> {
            this.handle(message);
        });
    }

    protected abstract void handle(Event message);

    void stop() {
        if (null != executorService && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
