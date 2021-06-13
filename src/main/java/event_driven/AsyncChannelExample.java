package event_driven;


import javax.xml.transform.Result;

public class AsyncChannelExample {
    public static void main(String[] args) {
        AsyncEventDispatcher dispatcher = new AsyncEventDispatcher();
        dispatcher.registerChannel(EventDispatcherExample.InputEvent.class, new AsyncInputEventHandler<EventDispatcherExample.InputEvent>(dispatcher));
        dispatcher.registerChannel(EventDispatcherExample.ResultEvent.class, new AsyncResultEventHandler<EventDispatcherExample.ResultEvent>(dispatcher));
        for (int i = 0; i < 10; i++) {
            dispatcher.dispatch(new EventDispatcherExample.InputEvent(i, 2));
        }
    }

    private static class AsyncInputEventHandler<T> extends AsyncChannel {
        private final AsyncEventDispatcher dispatcher;

        public AsyncInputEventHandler(AsyncEventDispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        @Override
        protected void handle(Event message) {
            // 接受到InputEvent
            EventDispatcherExample.InputEvent inputEvent;
            if (message instanceof EventDispatcherExample.InputEvent) {
                inputEvent = (EventDispatcherExample.InputEvent) message;
                System.out.println("x: " + inputEvent.getX() + ", y: " + inputEvent.getY());

                EventDispatcherExample.ResultEvent resultEvent = new EventDispatcherExample.ResultEvent(inputEvent.getX() + inputEvent.getY());
                dispatcher.dispatch(resultEvent);
            } else {
                throw new IllegalArgumentException("the message is not matched to inputevent handler");
            }

        }
    }

    private static class AsyncResultEventHandler<T> extends AsyncChannel {
        private final AsyncEventDispatcher dispatch;

        public AsyncResultEventHandler(AsyncEventDispatcher dispatcher) {
            this.dispatch = dispatcher;
        }

        @Override
        protected void handle(Event message) {
            EventDispatcherExample.ResultEvent resultEvent = (EventDispatcherExample.ResultEvent) message;
            System.out.println("the result is: " + resultEvent.getResult());
        }
    }
}
