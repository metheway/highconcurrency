package event_driven;

// 路由，就是取消息的looper
public interface DynamicRouter<E extends Message> {
    // 再路由上注册队列，因为路由需要从队列里面取消息
    void registerChannel(Class<? extends E> messageType, Channel<? extends E> channel);

    void dispatch(E message) throws EventDispatcher.MessageMatchException;
}
