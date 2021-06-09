package event_bus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Registry {
    // 线程安全的hashmap，保证多线程存储topic的安全。<topic, List<Subscriber>>
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer =
            new ConcurrentHashMap<>();

    // 队列主要保证顺序，并发性
    public void bind(Object subscriber) {
        List<Method> subscriberMethods = getSubscriberMethod(subscriber);
        subscriberMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    private void tierSubscriber(Object subscriber, Method method) {
        subscriberContainer.put("default-topic", new ConcurrentLinkedQueue<>());
    }

    private List<Method> getSubscriberMethod(Object subscriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> tmpClass = subscriber.getClass();
        // 遍历类的父类，找到符合要求的方法Subscribe注解、参数1个、public标记
        while (tmpClass != null) {
            Method[] declairedMethod = tmpClass.getDeclaredMethods();
            Arrays.stream(declairedMethod).filter(m -> m.isAnnotationPresent(Subscribe.class)
                    && m.getParameterCount() == 1 && m.getModifiers() == Modifier.PUBLIC).
                    forEach(methods::add);
            tmpClass = tmpClass.getSuperclass();
        }
        return methods;
    }

    public void clear() {
        this.subscriberContainer.clear();
    }
}