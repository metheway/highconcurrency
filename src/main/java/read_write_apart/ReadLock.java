package read_write_apart;

public class ReadLock implements Lock {
    private final ReadWriteLockImpl readWriteLock;

    public ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMUTEX()) {
            // 如果存在写线程争夺，或者偏向锁下有写线程等待，那么挂起成阻塞状态
            while (readWriteLock.getWritingWriters() > 0 || (
                    readWriteLock.isPreferWrite() && readWriteLock.getWritingWriters() > 0
            )) {
                readWriteLock.getMUTEX().wait();
            }
            // 成功获取锁
            readWriteLock.incrementReadingReaders();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMUTEX()) {
            readWriteLock.decrementReadingReaders();
            readWriteLock.changePrefer(true);
            // 释放锁，重新争夺
            readWriteLock.getMUTEX().notifyAll();
        }
    }
}
