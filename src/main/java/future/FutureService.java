package future;

public interface FutureService<IN, OUT> {
    Future<?> submit(Runnable runnable);
    Future<OUT> submit(Task<IN, OUT> task, IN input);
}
