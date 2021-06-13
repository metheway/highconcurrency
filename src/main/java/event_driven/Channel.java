package event_driven;

// 这个对应EDA里面的队列queue，接受泛型
public interface Channel<E extends Message> {
    void dispatch(E message);
}
