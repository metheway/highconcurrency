package event_bus;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorDirectories {
    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2);
        final EventBus eventBus = new AsyncEventBus(threadPoolExecutor);
        eventBus.register(new FileChangeListener());
        DirectoryTargetMonitor directoryTargetMonitor = new DirectoryTargetMonitor(eventBus, "/Users/junmenghuang/geektime/java/highconcurrency");
        directoryTargetMonitor.startMonitor();
    }

    private static class DirectoryTargetMonitor {
        private WatchService watchService;
        private final EventBus eventBus;
        private final Path path;
        private volatile boolean start = false;

        public DirectoryTargetMonitor(EventBus eventBus, String path) {
            this(eventBus, path, "");
        }

        public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath,
                                      final String... morePaths) {
            this.eventBus = eventBus;
            this.path = Paths.get(targetPath, morePaths);
        }

        public void startMonitor() throws IOException {
            this.watchService = FileSystems.getDefault().newWatchService();
            // 注册监听事件
            this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
            System.out.println("start monitoring the directory: " + path);
            this.start = start;
            while (true) {
                WatchKey watchKey = null;
                try {
                    watchKey = watchService.take();
                    watchKey.pollEvents().forEach(event -> {
                        WatchEvent.Kind<?> kind = event.kind();
                        Path path = (Path) event.context();
                        Path child = DirectoryTargetMonitor.this.path.resolve(path);
                        eventBus.post(new FileChangeEvent(child, kind));
                    });
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                    this.start = false;
                } finally {
                    if (watchKey != null) {
                        watchKey.reset();
                    }
                }
            }
        }

        public void stopWatch() {
            System.out.println("stop");
            Thread.currentThread().interrupt();
            this.start = false;
            try {
                this.watchService.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("stopped");

        }

        private static class FileChangeEvent {
            private final Path child;
            private final WatchEvent.Kind<?> kind;

            public FileChangeEvent(Path child, WatchEvent.Kind<?> kind) {
                this.child = child;
                this.kind = kind;
            }

            public Path getChild() {
                return child;
            }

            public WatchEvent.Kind<?> getKind() {
                return kind;
            }
        }
    }

    public static class FileChangeListener {
        @Subscribe
        public void onChange(DirectoryTargetMonitor.FileChangeEvent fileChangeEvent) {
            System.out.println("file changes: " + fileChangeEvent.getKind() + ": " + fileChangeEvent.getKind());
        }
    }
}
