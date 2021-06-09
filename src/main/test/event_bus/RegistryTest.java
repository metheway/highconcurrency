package event_bus;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RegistryTest {

    // 验证bindcase，bind一个object，然后将方法等放入同topic的hashmap里面。
    private final String caseTopic = "caseTopic";
    private final TestSubscriber caseSubscriber = new TestSubscriber(null, null);

    Registry registry = new Registry();
    Method[] methods = registry.getClass().getDeclaredMethods();
    Method getSubscriberMethod = null;
    List<Method> methodList;
    Object[] caseMethods;

    @Before
    public void setUp() throws Exception {
        for (Method method : methods) {
            if ("getSubscriberMethod".equals(method.getName())) {
                getSubscriberMethod = method;
            }
        }
        getSubscriberMethod.setAccessible(true);
        methodList = (List<Method>) getSubscriberMethod.invoke(registry, caseSubscriber);
        caseMethods = Arrays.stream(caseSubscriber.getClass().getDeclaredMethods()).filter(m ->
                m.isAnnotationPresent(Subscribe.class) && m.getParameterCount() == 1 &&
                        m.getModifiers() == Modifier.PUBLIC).toArray();
    }

    private class TestSubscriber extends Subscriber {

        public TestSubscriber(Object subscribeObject, Method subscribeMethod) {
            super(subscribeObject, subscribeMethod);
        }

        @Subscribe(topic = caseTopic)
        public void firstMethod(int i) {
            System.out.println("firstMethod: " + i);
        }

        @Subscribe(topic = caseTopic)
        public void secMethod(int i) {
            System.out.println("secMethod" + i);
        }

        public void thridMethod() {
            System.out.println("thirdMethod");
        }
    }

    @Test
    public void testBind() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Registry registry = new Registry();

        // 绑定后验证并发的hashmap是否已经置入
        registry.clear();
        Field subscriberContainer = registry.getClass().getDeclaredField("subscriberContainer");
        subscriberContainer.setAccessible(true);
        Method sizeOfHashMap = subscriberContainer.getClass().getMethod("size");
        Object sizeResult = sizeOfHashMap.invoke(subscriberContainer);

        System.out.println(sizeResult);
    }

    @Test
    public void testClear() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Registry registry = new Registry();

        // 注册一个吧
//        registry.bind(new Subscriber(caseSubscriber, (Method) caseMethods[0]));
        registry.bind(caseSubscriber);

        Field subscriberContainer = registry.getClass().getDeclaredField("subscriberContainer");
        subscriberContainer.setAccessible(true);

        Method sizeOfMapMethod = subscriberContainer.getType().getMethod("size");
        sizeOfMapMethod.setAccessible(true);

        Object fieldValue = subscriberContainer.get(registry);
        Integer length = (Integer) sizeOfMapMethod.invoke(fieldValue);
        assert (length > 0);

        // 调用clear后
        Method clearMethod = registry.getClass().getMethod("clear");
        clearMethod.invoke(registry);
        fieldValue = subscriberContainer.get(registry);
        length = (Integer) sizeOfMapMethod.invoke(fieldValue);
        assert (length == 0);
    }

    @Test
    public void testGetSubscriberMethod() throws InvocationTargetException, IllegalAccessException {
        // 确认获取的方法返回值一样
        assert (methodList.size() == caseMethods.length);
        for (int i = 0; i < caseMethods.length; i++) {
            assert (methodList.contains(caseMethods[i]));
        }
    }
}
