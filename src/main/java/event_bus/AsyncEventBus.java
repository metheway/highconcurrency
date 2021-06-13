package event_bus;

import event_bus.Bus;

import java.util.concurrent.ExecutorService;

public class AsyncEventBus implements Bus {
    public AsyncEventBus(String testBus, ExecutorService newFixedThreadPool) {

    }

    @Override
    public void register(Object subscriber) {

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
