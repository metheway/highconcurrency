package immutable_object;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// 用Immutable的性质来避免synchronized提供线程安全
public class ImmutableIntegerAccumulator {
    private final int init;

    public ImmutableIntegerAccumulator(int init) {
        this.init = init;
    }

    public ImmutableIntegerAccumulator(ImmutableIntegerAccumulator accumulator, int init) {
        this.init = init + accumulator.getValue();
    }

    private int getValue() {
        return init;
    }

    public ImmutableIntegerAccumulator add(int i) {
        return new ImmutableIntegerAccumulator(i + init);
    }

    public static void main(String[] args) {
        ImmutableIntegerAccumulator integerAccumulator = new ImmutableIntegerAccumulator(0);
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                int oldValue = integerAccumulator.getValue();
                int result = integerAccumulator.add(inc).getValue();
                System.out.println("oldValue: " + oldValue + ", inc: " + inc + ", result: " + result);
                if (inc + oldValue != result) {
                    System.out.println("error: " + ", oldValue: " + oldValue + ", inc: " + inc + ", result: " + result);
                }
                inc++;
                slowly();
            }
        }).start());
    }

    private static void slowly() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
