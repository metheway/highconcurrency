package read_write_apart;

// 有的资源读不冲突，写冲突，需要设计锁将读写分离
public interface Lock {
    void lock() throws InterruptedException;
    void unlock();
}
