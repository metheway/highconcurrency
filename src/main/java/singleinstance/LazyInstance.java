package singleinstance;

public final class LazyInstance {
    private byte[] data = new byte[1024];
    private static LazyInstance lazyInstance = null;

    private LazyInstance() {
    }

    // static保证了方法的唯一，但是无法保证单例
    public static LazyInstance getLazyInstance() {
        if (lazyInstance == null) {
            lazyInstance = new LazyInstance();
        }
        return lazyInstance;
    }

    public static synchronized LazyInstance concurrentGetInstance() {
        if (lazyInstance == null) {
            lazyInstance = new LazyInstance();
        }
        return lazyInstance;
    }
}
