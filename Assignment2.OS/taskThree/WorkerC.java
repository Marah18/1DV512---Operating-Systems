
public class WorkerC implements Runnable {
	CircularQ CircularQ;

	public WorkerC(CircularQ CircularQ) {
		super();
		this.CircularQ = CircularQ;
	}

	@Override
	public void run() {
		while (true) {
			try {
				CircularQ.Send('C');
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}