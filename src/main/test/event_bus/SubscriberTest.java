package event_bus;

import org.junit.Before;
import org.junit.Test;

// 这个测试没啥必要做，但是也做个实验下
public class SubscriberTest {

    private DefaultClass defaultClassObject = new DefaultClass();
    private Subscriber subscriber;

    @Before
    public void setUp() throws Exception {
        loadSubscriber();
    }

    private void loadSubscriber() throws NoSuchMethodException {
        subscriber = new Subscriber(defaultClassObject,
                defaultClassObject.getClass().getMethod("testMethod"));
    }

    public SubscriberTest() throws NoSuchMethodException {

    }

    @Test
    public void testConstructor() {
        Subscriber testSubscriber = new Subscriber(null, null);
        assert (testSubscriber != null);
    }

    @Test
    public void testGetSubscriber() throws NoSuchMethodException {
        if (subscriber == null) {
            loadSubscriber();
        }
        assert (subscriber.isDisable() == false);
        assert (subscriber.getSubscribeObject().equals(defaultClassObject));
        assert (subscriber.getSubscribeMethod().
                equals(defaultClassObject.getClass().getMethod("testMethod")));
    }

    @Test
    public void testSetSubscriber() throws NoSuchMethodException {
        if (subscriber == null) {
            loadSubscriber();
        }
        assert (subscriber.isDisable() == false);
        subscriber.setDisable(true);
        assert (subscriber.isDisable() == true);
        subscriber.setDisable(false);
        assert (subscriber.isDisable() == false);
    }

    private class DefaultClass {
        @Subscribe
        public void testMethod() {
            System.out.println("testMethod");
        }
    }
}
