import java.util.concurrent.Semaphore;

public class SemA implements Runnable {
	Semaphore semA;
	Semaphore semB;

	public SemA(Semaphore semaA, Semaphore semaB) {
		super();
		this.semA = semaA;
		semB = semaB;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				semA.acquire();
				System.out.print("A");
				semB.release();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
}