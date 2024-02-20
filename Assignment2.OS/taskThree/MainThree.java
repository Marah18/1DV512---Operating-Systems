public class MainThree {

    public static void main(String[] args) {

        // circular queue with size 5 elementes
        CircularQ CircularQ = new CircularQ(5);

        // Building thread to send char "A"
        WorkerA wa = new WorkerA(CircularQ);
        Thread senderA = new Thread(wa);

        // Building thread to send char "B"
        WorkerB wb = new WorkerB(CircularQ);
        Thread senderB = new Thread(wb);

        // Building thread to send char "C"
        WorkerC wc = new WorkerC(CircularQ);
        Thread senderC = new Thread(wc);

        // Building thread to recieve messages from different senders
        RecieverR wr = new RecieverR(CircularQ);
        Thread reciever = new Thread(wr);

        senderA.start();
        senderB.start();
        senderC.start();
        reciever.start();

    }

}