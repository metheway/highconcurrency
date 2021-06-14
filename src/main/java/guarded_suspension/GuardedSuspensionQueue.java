package guarded_suspension;

import java.util.LinkedList;

// 诸多设计模式的基础
public class GuardedSuspensionQueue {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private final int LIMIT = 10;

    public void offer(Integer data) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= LIMIT) {
                this.wait();
            }
            queue.addLast(data);
            this.notifyAll();
        }
    }

    public Integer take() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            // 这里的书上有bug
            Integer i = queue.removeFirst();
            this.notifyAll();
            return i;
        }
    }
}
