package read_write_apart;

public interface ReadWriteLock {
    Lock readLock();
    Lock writeLock();
    int getWritingWriters();
    int getReadingReaders();
    static ReadWriteLock readWriteLock() {
        return new ReadWriteLockImpl();
    }
    static ReadWriteLock readWriteLock(boolean preferWrite) {
        return new ReadWriteLockImpl(preferWrite);
    }
}
