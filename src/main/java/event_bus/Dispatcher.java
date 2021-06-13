package event_bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

// 将event推送给每个topic的subscriber上面
public class Dispatcher {
    private final Executor excecutorService;
    private final EventExceptionHandler eventExceptionHandler;

    // 特定的一个线程
    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;
    public static final Executor PRE_EXECUTOR_SERVICE = PreExecutorService.INSTANCE;

    public Dispatcher(EventExceptionHandler exceptionHandler, Executor executor) {
        this.eventExceptionHandler = exceptionHandler;
        this.excecutorService = executor;
    }


    public static Dispatcher newDispatcher(EventExceptionHandler exceptionHandler, Executor excecutor) {
        return null;
    }

    public void dispatch(EventBus eventBus, Registry registry, Object event, String topic) {
        // 取出响应topic的所有Subscriber
        ConcurrentLinkedQueue<Subscriber> concurrentLinkedQueue = registry.scanSubscriber(topic);
        // 将Subscriber里面的方法进行调用
        concurrentLinkedQueue.stream().filter(subscriber -> !subscriber.isDisable()).
                filter(subscriber -> {
                    Method method = subscriber.getSubscribeMethod();
                    Class<?> clazz = method.getParameterTypes()[0];
                    // 获取类型，判断方法是否是event的父类
                    return (clazz.isAssignableFrom(event.getClass()));
                }).
                forEach(subscriber -> realInvokeSubscribe(subscriber, event, eventBus));
    }

    private void realInvokeSubscribe(Object subscriber, Object event, EventBus eventBus) {
        Method method = ((Subscriber) subscriber).getSubscribeMethod();
        Object subscriberObject = ((Subscriber) subscriber).getSubscribeObject();
        excecutorService.execute(() -> {
            try {
                method.invoke(subscriberObject, event);
            } catch (Exception e) {
                if (null != eventExceptionHandler) {
                    eventExceptionHandler.handle(e, new BaseEventContext(
                            eventBus.getBusName(), subscriber, event
                    ));
                }
            }
        });
    }

    public static class SeqExecutorService implements Executor {
        public static final Executor INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PreExecutorService implements Executor {
        public static final Executor INSTANCE = new PreExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

}
