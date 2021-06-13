package read_write_apart;

public class ReadWriteLockImpl implements ReadWriteLock {
    private boolean preferWrite;
    private final Object MUTEX = new Object();
    private int writingWriters = 0;
    private int waitingWriters = 0;
    private int readingReaders = 0;

    public ReadWriteLockImpl(boolean preferWrite) {
        this.preferWrite = preferWrite;
    }

    public ReadWriteLockImpl() {
        this(true);
    }

    @Override
    public Lock readLock() {
        return new ReadLock(this);
    }

    @Override
    public Lock writeLock() {
        return new WriteLock(this);
    }

    @Override
    public int getWritingWriters() {
        return writingWriters;
    }

    @Override
    public int getReadingReaders() {
        return readingReaders;
    }

    void incrementWritingWriters() {
        this.writingWriters++;
    }

    void decrementWritingWriters() {
        this.writingWriters--;
    }

    void incrementWaitingWriters() {
        this.waitingWriters++;
    }

    void decrementWaitingWriters() {
        this.waitingWriters--;
    }

    void incrementReadingReaders() {
        this.readingReaders++;
    }

    void decrementReadingReaders() {
        this.readingReaders--;
    }

    public Object getMUTEX() {
        return MUTEX;
    }

    public boolean isPreferWrite() {
        return preferWrite;
    }

    public void changePrefer(boolean preferWrite) {
        this.preferWrite = preferWrite;
    }
}
