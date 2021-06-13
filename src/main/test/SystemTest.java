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
        public void methodFirstObjectFirst(String a) {
            System.out.println("----------1-1------: " + a);
        }

        @Subscribe(topic = "first")
        public void methodSecObjectFirst(String a) {
            System.out.println("1-2: " + a);
        }
    }

    private static class SimpleObject2 {
        @Subscribe
        public void methodFirstObjectSec(String a) {
            System.out.println("2-1: " + a);
        }

        @Subscribe(topic = "sec")
        public void methodSecObjectSec(String a) {
            System.out.println("2-2: " + a);
        }
    }
}
