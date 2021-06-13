package active_object;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ActiveDaemonThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        OrderService orderService = active(new OrderServiceImpl());
        Future<String> future = orderService.findOrderDetails(1234);
        System.out.println("i will be back immediately");
        System.out.println(future.get());
    }

    private static OrderService active(OrderServiceImpl orderService) {
        return null;
    }
}