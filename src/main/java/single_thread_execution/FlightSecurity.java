package single_thread_execution;

// 这个也太简单了
public class FlightSecurity {
    private int count = 0;
    private String boardingPass = null;
    private String idCard = null;

    public synchronized void pass(String boardingPass, String idCard) {
        this.boardingPass = boardingPass;
        this.idCard = idCard;
        this.count++;
        check();
    }

    private void check() {
        if (boardingPass.charAt(0) != idCard.charAt(0)) {
            throw new RuntimeException("exception: " + toString());
        }
    }

    @Override
    public String toString() {
        return "FlightSecurity{" +
                "count=" + count +
                ", boardingPass='" + boardingPass + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
