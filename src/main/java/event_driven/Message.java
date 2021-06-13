package event_driven;

// 这个接口主要是对应Event，实际上就是一个高级的可扩展的Event
public interface Message {
    // 类型，需要继承Message，表示自己的类型
    Class<? extends Message> getType();
}
