import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Semaphore semA = new Semaphore(1);
		Semaphore semB = new Semaphore(0);

		SemA wa = new SemA(semA, semB);
		Thread ta = new Thread(wa);
		ta.start();

		SemB wb = new SemB(semA, semB);
		Thread tb = new Thread(wb);
		tb.start();

		try {
			ta.join();
			tb.join();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
