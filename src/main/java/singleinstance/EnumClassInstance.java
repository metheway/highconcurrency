package singleinstance;

public class EnumClassInstance {
    private byte[] data = new byte[1024];

    public EnumClassInstance() {
    }

    private enum Holder {
        INSTANCE;

        private EnumClassInstance instance;

        Holder() {
            instance = new EnumClassInstance();
        }

        private EnumClassInstance getInstance() {
            return instance;
        }
    }

    // 由于Holder是枚举，所以是单例且是线程安全的，所以调用的方法也是单例的
    public static EnumClassInstance getEnumClassInstance() {
        return Holder.INSTANCE.getInstance();
    }
}
