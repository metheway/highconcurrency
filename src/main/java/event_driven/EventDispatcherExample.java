package event_driven;

public class EventDispatcherExample {
    public static void main(String[] args) {
        EventDispatcher eventDispatcher = new EventDispatcher();
        eventDispatcher.registerChannel(InputEvent.class, new InputEventHandler(eventDispatcher));
        eventDispatcher.registerChannel(ResultEvent.class, new ResultEventHandler(eventDispatcher));
        eventDispatcher.dispatch(new InputEvent(1, 2));
    }

    private static class InputEvent extends Event {
        private final int x;
        private final int y;

        public InputEvent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    static class ResultEvent extends Event {
        private final int result;

        public ResultEvent(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }
    }

    private static class InputEventHandler implements Channel<InputEvent> {
        private final EventDispatcher eventDispatcher;
        public InputEventHandler(EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        @Override
        public void dispatch(InputEvent message) {
            System.out.println("x: " + message.getX() + ", y: " + message.getY());
            int result = message.getX() + message.getY();
            eventDispatcher.dispatch(new ResultEvent(result));
        }
    }

    private static class ResultEventHandler implements Channel<ResultEvent> {
        private final EventDispatcher eventDispatcher;

        public ResultEventHandler(EventDispatcher eventDispatcher) {
            this.eventDispatcher = eventDispatcher;
        }

        @Override
        public void dispatch(ResultEvent message) {
            System.out.println("the result is: " + message.getResult());
        }
    }
}
