import java.util.concurrent.Semaphore;

public class MainTwo {

	public static void main(String[] args) {
		Semaphore semA = new Semaphore(1);
		Semaphore semB = new Semaphore(0);
		Semaphore semC = new Semaphore(0);
		Semaphore semD = new Semaphore(0);

		AWorker wa = new AWorker(semA, semB, semC);
		Thread ta = new Thread(wa);
		ta.start();

		BWorker wb = new BWorker(semA, semB);
		Thread tb = new Thread(wb);
		tb.start();

		CWorker wc = new CWorker(semA, semC, semD);
		Thread tc = new Thread(wc);
		tc.start();

		DWorker wd = new DWorker(semC, semD);
		Thread td = new Thread(wd);
		td.start();
		try {
			ta.join();
			tb.join();
			tc.join();
			td.join();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
