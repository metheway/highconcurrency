package future;

import java.util.concurrent.TimeUnit;

public class FutureTest {
    public static void main(String[] args) {
        FutureService<Void, Void> futureService = FutureService.newService();
        Future<?> future = futureService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.println("i am finished");
        });
        try {
            future.get();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


}
