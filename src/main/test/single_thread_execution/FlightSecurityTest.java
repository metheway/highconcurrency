package single_thread_execution;

public class FlightSecurityTest {
    static class Passengers extends Thread {
        private final FlightSecurity flightSecurity;
        private final String idCard;
        private final String boardingPass;

        public Passengers(FlightSecurity flightSecurity, String idCard, String boardingPass) {
            this.flightSecurity = flightSecurity;
            this.idCard = idCard;
            this.boardingPass = boardingPass;
        }

        @Override
        public void run() {
            while (true) {
                flightSecurity.pass(boardingPass, idCard);
            }
        }
    }

    public static void main(String[] args) {
        final FlightSecurity flightSecurity = new FlightSecurity();
        new Passengers(flightSecurity, "A1234", "A1234").start();
        new Passengers(flightSecurity, "B2234", "B2234").start();
        new Passengers(flightSecurity, "C1234", "C1234").start();
    }
}
