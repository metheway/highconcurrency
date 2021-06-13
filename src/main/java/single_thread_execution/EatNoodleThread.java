package single_thread_execution;

// 还有设计成放在同一种类里面的TablewarePair我就不展示了
public class EatNoodleThread extends Thread {
    private final String name;
    private final TableWare leftTool;
    private final TableWare rightTool;

    public EatNoodleThread(String name, TableWare leftTool, TableWare rightTool) {
        this.name = name;
        this.leftTool = leftTool;
        this.rightTool = rightTool;
    }

    @Override
    public void run() {
        while (true) {
            this.eat();
        }
    }

    private void eat() {
        synchronized (leftTool) {
            System.out.println(name + " take up: " + leftTool + " left");
            synchronized (rightTool) {
                System.out.println(name + " take up: " + rightTool + "right");
                System.out.println("eating");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                System.out.println(name + " put down: " + rightTool + "right");
            }
            System.out.println(name + " put down: " + leftTool + "left");
        }
    }

    public static void main(String[] args) {
        TableWare fork = new TableWare("fork");
        TableWare knife = new TableWare("knife");
        for (int i = 0; i < 100; i++) {
            new EatNoodleThread(String.valueOf(i), fork, knife).start();
        }
    }
}