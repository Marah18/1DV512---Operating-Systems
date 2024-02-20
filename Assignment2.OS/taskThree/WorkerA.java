
public class WorkerA implements Runnable {
	CircularQ CircularQ;

	public WorkerA(CircularQ CircularQ) {
		super();
		this.CircularQ = CircularQ;
	}

	@Override
	public void run() {
		while (true) {
			try {
				CircularQ.Send('A');
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
