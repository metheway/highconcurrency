package observablethread;

public interface Observable {
    enum Cycle {
        STARTED, RUNNING, DONE, ERROR;
    }

    // 必须要获取生命周期状态来进行观察
    Cycle getCycle();

    // 启动线程的方法，为了屏蔽原先的Thread方法
    void start();

    void interrupt();
}
