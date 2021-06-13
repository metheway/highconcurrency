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

    // 队列主要保证顺序，并发性，将有注解存入
    public void bind(Object subscriber) {
        List<Method> subscriberMethods = getSubscriberMethod(subscriber);
        subscriberMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    // 将对应的subscriber和method建立联系，放入对应的topic里面
    private void tierSubscriber(Object subscriber, Method method) {
        // <topic, queue>, queue -> (subscriber, method)
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        // hashmap容器判断是否存在，如果存在则实例化一个quueu
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        // 创建一个Subsriber并且加入Subscriber列表中(为什么不直接加入Subscriber对象呢，新实例化一个Subscriber）
        subscriberContainer.get(topic).add(new Subscriber(subscriber, method));
    }

    // 获取提交者的有注解的方法
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

    public void unbind(Object subscriber) {
        // 解绑只是设置非法，而非删掉，有待改进
        subscriberContainer.forEach((key, queue) -> {
            queue.forEach(s -> {
                if (s.getSubscribeObject() == subscriber) {
                    s.setDisable(true);
                }
            });
        });
    }

    public void clear() {
        this.subscriberContainer.clear();
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);
    }
}