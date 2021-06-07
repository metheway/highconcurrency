package observablethread;

public class EmptyLifeCircle<T> implements TaskLifeCircle<T> {
    @Override
    public void onStart(Thread thread) {

    }

    @Override
    public void onRunning(Thread thread) {

    }

    @Override
    public void onFinish(Thread thread, T result) {
        
    }

    @Override
    public void onError(Thread thread, Exception e) {

    }
}
