import java.util.concurrent.Semaphore;

public class SemB implements Runnable {

	Semaphore semA;
	Semaphore semB;

	public SemB(Semaphore semaA, Semaphore semaB) {
		super();
		this.semA = semaA;
		semB = semaB;
	}

	@Override

	public void run() {

		for (int i = 0; i < 10; i++) {
			try {
				semB.acquire();
				System.out.print("B");
				semA.release();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}
}
