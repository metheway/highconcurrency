package event_bus;

//  核心类也就是Bus、Subscriber，如果还有其他的再加上Registry
public interface Bus {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object Event, String topic);

    void close();

    String getBusName();
}
