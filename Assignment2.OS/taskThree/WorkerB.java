public class WorkerB implements Runnable {
	CircularQ CircularQ;

	public WorkerB(CircularQ CircularQ) {
		super();
		this.CircularQ = CircularQ;
	}

	@Override
	public void run() {
		while (true) {
			try {
				CircularQ.Send('B');
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