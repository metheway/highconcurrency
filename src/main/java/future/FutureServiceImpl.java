package future;

public class FutureServiceImpl<IN, OUT> implements FutureService<IN, OUT> {
    @Override
    public Future<?> submit(Runnable runnable) {
        return null;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input) {
        return null;
    }
}
