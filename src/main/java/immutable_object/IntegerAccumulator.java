package immutable_object;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class IntegerAccumulator {
    private int init;

    public IntegerAccumulator(int init) {
        this.init = init;
    }

    public int add(int i) {
        this.init += i;
        return this.init;
    }

    public int getValue() {
        return init;
    }

    public static void main(String[] args) {
        IntegerAccumulator accumulator = new IntegerAccumulator(0);
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                int oldValue = accumulator.getValue();
                int result = accumulator.add(inc);
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
