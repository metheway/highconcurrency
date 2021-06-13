package event_bus;

public interface EventExceptionHandler {
    void handle(Exception e, EventContext eventContext);
}
