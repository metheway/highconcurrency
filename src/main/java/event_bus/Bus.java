package event_bus;

// 核心类也就是Bus、Subscriber，如果还有其他的再加上Registry
// 这个框架实现的功能就是根据注解不同进行函数的调用（用事件驱动）
public interface Bus {
    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object Event, String topic);

    void close();

    String getBusName();
}
