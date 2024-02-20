import java.util.concurrent.Semaphore;

public class CWorker implements Runnable {
	Semaphore semA;
	Semaphore semC;
	Semaphore semD;

	public CWorker(Semaphore semaA, Semaphore semaC, Semaphore semaD) {
		super();
		this.semA = semaA;
		semD = semaD;
		this.semC = semaC;
	}

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				try {
					semC.acquire();
					System.out.print("C");
					semD.release();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {

				try {
					semC.acquire();
					System.out.print("C");
					semA.release();
				} catch (Exception e) {
					System.out.println(e);
				}

			}
		}

	}
}