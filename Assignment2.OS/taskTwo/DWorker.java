import java.util.concurrent.Semaphore;

public class DWorker implements Runnable {
	Semaphore semC;
	Semaphore semD;

	public DWorker(Semaphore semaC, Semaphore semaD) {
		super();
		this.semC = semaC;
		semD = semaD;
	}

	@Override
	public void run() {

		for (int i = 0; i < 5; i++) {

			try {
				semD.acquire();
				System.out.print("D");
				semC.release();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
}