package thread_api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlightQueryExample {
    private static List<String> flightCompany = Arrays.asList("CCC", "AAA", "BBB");

    // 搜索从起点到终点的航班有哪些
    private static List<String> search(String original, String dest) {
        // 将上述的字符串转换成task任务
        final List<String> result = new ArrayList<>();

        List<FlightQueryTask> tasks = flightCompany.stream().map(flightName -> createSearchTask(
            flightName, original, dest
        )).collect(Collectors.toList());

        // 启动任务
        for (FlightQueryTask task:
             tasks) {
            task.start();
        }

        // 保证执行完，查询完
        for (FlightQueryTask task: tasks) {
            try {
                task.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        // 获取查询结果
        tasks.stream().map(FlightQueryTask::get).forEach(result::addAll);
        System.out.println("lalala: " + result);
        return result;
    }

    private static FlightQueryTask createSearchTask(String flightName, String original, String dest) {
        return new FlightQueryTask(flightName, original, dest);
    }

    public static void main(String[] args) {
        List<String> results = search("a", "b");
        System.out.println("----------result------------");
        results.forEach(System.out::println);
    }
}
