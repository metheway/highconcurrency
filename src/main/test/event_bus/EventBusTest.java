package event_bus;

import org.junit.Test;

public class EventBusTest {
    @Test
    public void testRegister() {
        EventBus eventBus = new EventBus();
        eventBus.register(null);
    }

    @Test
    public void testEventBus() {
        // 测试总线可以根据注册的主题进行事件响应（根据不同的主题对不同的订阅者（函数）进行响应（调用））
        Bus bus = new EventBus("testBus");
        bus.register(new Subscriber(null, null));
        bus.register(new Subscriber(null, null));
        bus.post("hello");
        System.out.println("------------------");
        bus.post("hello", "test");
    }
}
