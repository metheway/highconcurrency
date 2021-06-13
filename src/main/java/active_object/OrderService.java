package active_object;

import java.util.concurrent.Future;

public interface OrderService {
    public Future<String> findOrderDetails(long order);

    void order(String account, long orderId);
}
