package future;

public interface FutureService<IN, OUT> {
    static FutureService newService() {
        return new FutureServiceImpl();
    }

    Future<?> submit(Runnable runnable);
    Future<OUT> submit(Task<IN, OUT> task, IN input);
    Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback);
}
