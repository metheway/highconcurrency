package event_bus;

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
        return new Dispatcher(exceptionHandler, excecutor);
    }

    public void dispatch(EventBus eventBus, Registry registry, Object event, String topic) {
        // 取出响应topic的所有Subscriber
        ConcurrentLinkedQueue<Subscriber> concurrentLinkedQueue = registry.scanSubscriber(topic);
        // 将Subscriber里面的方法进行调用
        concurrentLinkedQueue.stream().filter(subscriber -> !subscriber.isDisable()).
                filter(subscriber -> {
                    Method method = subscriber.getSubscribeMethod();
                    Class<?> clazz = method.getParameterTypes()[0];
                    // 获取参数类型，判断方法是否是event的父类或一样的类
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
                System.out.println("e: " + e);
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

    private static class BaseEventContext implements EventContext {

        private final String eventBusName;
        private final Subscriber subscriber;
        private final Object event;

        public BaseEventContext(String busName, Object subscriber, Object event) {
            this.eventBusName = busName;
            this.subscriber = (Subscriber) subscriber;
            this.event = event;
        }

        @Override
        public String getEventBusName() {
            return eventBusName;
        }

        @Override
        public Subscriber getSubscriber() {
            return subscriber;
        }

        @Override
        public Method getSubscribe() {
            return subscriber != null ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return event;
        }
    }

}
