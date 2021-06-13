package event_bus;

import java.util.concurrent.Executor;

// 将event推送给每个topic的subscriber上面
public class Dispatcher {
    private final Executor excecutorService;
    private final EventExceptionHandler eventExceptionHandler;

    // 特定的一个线程
    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;
    public static final Executor PRE_EXECUTOR_SERVICE = PreExecutorService.INSTANCE;

    public Dispatcher(EventExceptionHandler exceptionHandler, Executor executor) {
        this.eventExceptionHandler = exceptionHandler;
        this.excecutorService = executor;
    }


    public static Dispatcher newDispatcher(EventExceptionHandler exceptionHandler, Executor excecutor) {
        return null;
    }

    public static class SeqExecutorService implements Executor {
        public static final Executor INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PreExecutorService implements Executor {
        public static final Executor INSTANCE = new PreExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

}
