import java.util.concurrent.Semaphore;

public class AWorker implements Runnable {
	Semaphore semA;
	Semaphore semB;
	Semaphore semC;

	public AWorker(Semaphore semaA, Semaphore semaB, Semaphore semC) {
		super();
		this.semA = semaA;
		semB = semaB;
		this.semC = semC;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				try {
					semA.acquire();
					System.out.print("A");
					semB.release();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				try {
					semA.acquire();
					System.out.print("A");
					semC.release();
				} catch (Exception e) {
					System.out.println(e);
				}
			}

		}

	}
}