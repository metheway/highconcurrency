package future;

public interface Task<IN, OUT> {
    OUT get(IN input);
}
