package thread_api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FlightQueryTask extends Thread implements FlightQuery {
    private final List<String> flightList = new ArrayList<>();

    private final String origin;
    private final String dest;

    @Override
    public List<String> get() {
        return flightList;
    }

    public FlightQueryTask(String airLine, String origin, String dest) {
        // 实例化airLine这个线程
        super("[" + airLine + "]");
        this.origin = origin;
        this.dest = dest;
    }

    @Override
    public void run() {
        System.out.printf("%s-query from %s to %s \n", getName(), origin, dest);
        int randomVal = ThreadLocalRandom.current().nextInt(10);
        try {
            // 模拟一下等几秒查询
            TimeUnit.SECONDS.sleep(randomVal);
            // 模拟下航班为啥
            this.flightList.add(getName() + "-" + randomVal);
            System.out.printf("the flight: %s list query successfully\n", getName());
        } catch (InterruptedException interruptedException) {
            // 可中断的线程，抓取异常吞掉！
        }
        // 这个主要是执行Runnable()对象的，不去掉，保证Thread(Runnable)构造函数还有效
        super.run();
    }
}
