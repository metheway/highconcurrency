package active_object;

import java.util.concurrent.Future;

public class OrderServiceProxy implements OrderService{
    public OrderServiceProxy() {

    }

    @Override
    public Future<String> findOrderDetails(long order) {
        return null;
    }

    @Override
    public void order(String account, long orderId) {

    }
}
