package observablethread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ObservableThread<T> extends Thread implements Observable {

    private final TaskLifeCircle<T> lifeCircle;
    private final Task<T> task;
    private Cycle cycle;

    public ObservableThread(Task<T> task) {
        this(new EmptyLifeCircle<>(), task);
    }

    public ObservableThread(TaskLifeCircle<T> lifeCircle, Task<T> task) {
        super();
        if (task == null) {
            throw new IllegalArgumentException("The task is required");
        }
        this.lifeCircle = lifeCircle;
        this.task = task;
    }

    @Override
    public void run() {
        // 执行线程逻辑前，先触发相应事件
        this.update(Cycle.STARTED, null, null);
        try {
            this.update(Cycle.RUNNING, null, null);
            T result = this.task.call();
            this.update(Cycle.DONE, result, null);
        } catch (Exception e) {
            this.update(Cycle.ERROR, null, e);
        }
    }

    private void update(Cycle cycle, T result, Exception e) {
        this.cycle = cycle;
        if (lifeCircle == null) {
            return;
        }
        try {
            switch (cycle) {
                case STARTED:
                    this.lifeCircle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifeCircle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifeCircle.onFinish(currentThread(), result);
                    break;
                case ERROR:
                    this.lifeCircle.onError(currentThread(), e);
                    break;
            }
        } catch (Exception ex) {
            if (cycle == Cycle.ERROR) {
                throw ex;
            }
        }
    }

    @Override
    public Cycle getCycle() {
        return this.cycle;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void interrupt() {
    }

    public static void main(String[] args) {

        final TaskLifeCircle<String> taskLifeCircle = new TaskLifeCircle<>() {

            @Override
            public void onStart(Thread thread) {
                System.out.println("start");
            }

            @Override
            public void onRunning(Thread thread) {
                System.out.println("running");
            }

            @Override
            public void onFinish(Thread thread, String result) {
                System.out.println("finish: " + result);
            }

            @Override
            public void onError(Thread thread, Exception e) {
                System.out.println("error");
            }
        };

        CountDownLatch latch = new CountDownLatch(1);
        Observable observableThread = new ObservableThread<>(taskLifeCircle, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task finished done.");
            latch.countDown();
            return "hello observer";
        });

        observableThread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
