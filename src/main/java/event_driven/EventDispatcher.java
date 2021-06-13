package event_driven;

import java.util.HashMap;
import java.util.Map;

public class EventDispatcher implements DynamicRouter {
    // 表示根据转发消息的类型建立通道（队列）
    private final Map<Class<? extends Message>, Channel> routerTable;

    public EventDispatcher() {
        // 不用并发的？
        routerTable = new HashMap<>();
    }

    @Override
    public void registerChannel(Class messageType, Channel channel) {
        this.routerTable.put(messageType, channel);
    }

    @Override
    public void dispatch(Message message) {
        if (routerTable.containsKey(message.getType())) {
            routerTable.get(message.getType()).dispatch(message);
        } else {
            throw new MessageMatchException("can't match the channel for the messagetype: " + message.getType());
        }
    }

    // 运行时异常，不是必须在方法上继续抛出
    public static class MessageMatchException extends RuntimeException {
        public MessageMatchException(String s) {
            super(s);
        }
    }
}
