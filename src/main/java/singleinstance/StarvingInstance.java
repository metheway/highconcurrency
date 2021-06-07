package singleinstance;

// 饿汉模式，可以保证多线程下单例
// 但是资源占用时间长
public final class StarvingInstance {
    private byte[] data = new byte[1024];
    private static StarvingInstance starvingInstance = new StarvingInstance();

    private StarvingInstance() {
    }

    public StarvingInstance getStarvingInstance() {
        return starvingInstance;
    }
}
