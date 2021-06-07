package singleinstance;

import java.net.Socket;

public final class DoubleCheckInstance {
    private byte[] data = new byte[1024];
    // 加入volatile主要是防止指令重排序
    private static volatile DoubleCheckInstance doubleCheckInstance;

    private Object object;
    private Socket socket;

    private DoubleCheckInstance() {
        // 如果没有关键字volatile，可能导致空指针异常，因为根据Happens-Before和指令重排序规则，无前后约束关系
        this.object = new Object();
        this.socket = new Socket();
    }

    public DoubleCheckInstance getDoubleCheckInstance() {
        if (doubleCheckInstance == null) {
            synchronized (DoubleCheckInstance.class) {
                doubleCheckInstance = new DoubleCheckInstance();
            }
        }
        return doubleCheckInstance;
    }
}
