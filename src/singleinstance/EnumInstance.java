package singleinstance;

// 枚举本身就是final，且不可反射
public enum EnumInstance {
    INSTANCE;

    private byte[] data = new byte[1024];

    private EnumInstance() {
    }

    public static void method() {
        // 调用方法，则回主动使用EnumInstance，从而进行实例化INSTANCE
    }

    public static EnumInstance getInstance() {
        return INSTANCE;
    }
}
