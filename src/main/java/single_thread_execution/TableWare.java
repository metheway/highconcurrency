package single_thread_execution;

// 吃面问题，仍然很简单
public class TableWare {
    private final String toolName;

    public TableWare(String toolName) {
        this.toolName = toolName;
    }

    @Override
    public String toString() {
        return "TableWare{" +
                "toolName='" + toolName + '\'' +
                '}';
    }
}
