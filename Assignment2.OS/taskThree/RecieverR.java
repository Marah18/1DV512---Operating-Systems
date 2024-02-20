public class RecieverR implements Runnable {
    CircularQ CircularQ;

    public RecieverR(CircularQ CircularQ) {
        super();
        this.CircularQ = CircularQ;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Recieve the message and print its content
                System.out.print(CircularQ.Recv());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
