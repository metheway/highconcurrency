package observablethread;

public interface TaskLifeCircle<T> {
    void onStart(Thread thread);

    void onRunning(Thread thread);

    void onFinish(Thread thread, T result);

    void onError(Thread thread, Exception e);
}
