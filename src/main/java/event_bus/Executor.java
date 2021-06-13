package event_bus;

public interface Executor {
    void execute(Runnable command);
}
