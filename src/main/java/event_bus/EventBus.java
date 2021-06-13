package event_bus;

import java.util.concurrent.Executor;

public class EventBus implements Bus {
    private final Registry registry = new Registry();
    private String busName;
    private final static String DEFUALT_BUS_NAME = "default";
    private final static String DEFUALT_TOPIC = "default-topic";
    private final Dispatcher dispatcher;

    public EventBus() {
        this(DEFUALT_BUS_NAME, null, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    EventBus(String busName, EventExceptionHandler exceptionHandler, Executor excecutor) {
        this.busName = busName;
        this.dispatcher = Dispatcher.newDispatcher(exceptionHandler, excecutor);
    }

    public EventBus(String busName) {
        this(busName, null, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(EventExceptionHandler exceptionHandler) {
        this(DEFUALT_BUS_NAME, exceptionHandler, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {

    }

    @Override
    public void post(Object event) {

    }

    @Override
    public void post(Object Event, String topic) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getBusName() {
        return null;
    }



}
