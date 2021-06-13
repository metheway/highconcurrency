import event_bus.AsyncEventBus;
import event_bus.Bus;
import event_bus.EventBus;
import event_bus.Subscribe;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SystemTest {
    static class SyncTest {
        public static void main(String[] args) {
            Bus bus = new EventBus();
            bus.register(new SimpleObject1());
            bus.register(new SimpleObject2());
            bus.post("hello");
            System.out.println("========================");
            bus.post("hello", "first");
        }
    }
    static class AsyncTest {
        public static void main(String[] args) {
            Bus bus = new AsyncEventBus("TestBus", Executors.newFixedThreadPool(10));
            bus.register(new SimpleObject1());
            bus.register(new SimpleObject2());
            for (int i = 0; i < 10; i++) {
                bus.post("hello");
            }
            System.out.println("================================");
            for (int i = 0; i < 10; i++) {
                bus.post("hello", "first");
            }
        }
    }

    private static class SimpleObject1 {
        @Subscribe
        public void methodFirstObjectFirst() {
            System.out.println("-------methodFirstObjectFirst---1-1------");
        }

        @Subscribe(topic = "first")
        public void methodSecObjectFirst() {
            System.out.println("methodSecObjectFirst: 1-2");
        }
    }

    private static class SimpleObject2 {
        @Subscribe
        public void methodFirstObjectSec() {
            System.out.println("2-1");
        }

        @Subscribe(topic = "sec")
        public void methodSecObjectSec() {
            System.out.println("2-2");
        }
    }
}
