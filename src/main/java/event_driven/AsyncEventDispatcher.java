package event_driven;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncEventDispatcher implements DynamicRouter<Event> {
    // 根据事件的类型进行存储，相应类型的事件可以进行相应的Handler设置
    private final Map<Class<? extends Event>, AsyncChannel> routeTable;

    public AsyncEventDispatcher() {
        this.routeTable = new ConcurrentHashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Event> messageType, Channel<? extends Event> channel) {
        if (!(channel instanceof AsyncChannel)) {
            throw new IllegalArgumentException("the channel must be asyncchannel type. ");
        }
        this.routeTable.put(messageType, (AsyncChannel) channel);
    }

    @Override
    public void dispatch(Event message) {
        // 调用整个channel里面这个类的消息处理;
        if (routeTable.get(message.getType()) != null) {
            routeTable.get(message.getType()).dispatch(message);
        } else {
            throw new EventDispatcher.MessageMatchException("can't match the channel for: " + message.getType());
        }
    }

    public void shutdown() {
        routeTable.values().forEach(AsyncChannel::stop);
    }
}
