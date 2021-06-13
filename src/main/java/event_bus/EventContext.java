package event_bus;

import java.lang.reflect.Method;

public interface EventContext {
    String getEventBusName();
    Object getSubscriber();
    Method getSubscribe();
    Object getEvent();
}
