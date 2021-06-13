package immutable_object;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// 设置为不可变对象，那么就可以不用synchronized来保证并发了
public class ArrayListStream {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Java", "Thread", "Concurrency");
        list.parallelStream().map(String::toUpperCase).forEach(System.out::println);
        list.stream().forEach(System.out::println);
    }
}
