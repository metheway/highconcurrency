package event_driven;

public class EventDispatcherExample {
    public static void main(String[] args) {
        EventDispatcher eventDispatcher = new EventDispatcher();
        eventDispatcher.registerChannel(InputEvent.class, new InputEventHandler(eventDispatcher));
        eventDispatcher.dispatch(new InputEvent(1, 2));
    }

    private static class InputEvent implements Message {
        public InputEvent(int i, int j) {

        }

        @Override
        public Class<? extends Message> getType() {
            return getClass();
        }
    }

    private static class InputEventHandler implements Channel {
        public InputEventHandler(EventDispatcher eventDispatcher) {
        }

        @Override
        public void dispatch(Message message) {

        }
    }
}
