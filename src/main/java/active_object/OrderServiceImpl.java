package active_object;

import java.util.concurrent.Future;

public class OrderServiceImpl implements OrderService {
    @Override
    public Future<String> findOrderDetails(long order) {
        return null;
    }

    @Override
    public void order(String account, long orderId) {

    }
}
