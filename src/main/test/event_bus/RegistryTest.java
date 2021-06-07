package event_bus;

import org.junit.Test;

public class RegistryTest {
    @Test
    public void testBind() {
        Registry registry = new Registry();
        registry.bind(null);

        registry.bind(new Subscriber(null, null));
    }
}
